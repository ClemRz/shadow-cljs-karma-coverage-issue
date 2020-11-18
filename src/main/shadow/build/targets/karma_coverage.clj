(ns shadow.build.targets.karma-coverage
  (:refer-clojure :exclude (compile flush resolve))
  (:require
    [shadow.build :as build]
    [shadow.build.modules :as modules]
    [hiccup.page :refer (html5)]
    [clojure.java.io :as io]
    [shadow.build.api :as build-api]
    [shadow.build.output :as output]
    [shadow.build.data :as data]
    [shadow.build.test-util :as tu]
    [clojure.data.json :as json]))

(defn configure [state mode {:keys [runner-ns output-to js-options] :or {runner-ns 'shadow.test.karma} :as config}]
  (let [output-to
        (io/file output-to)

        output-dir
        (.getParentFile output-to)]

    (io/make-parents output-to)

    (-> state
        (tu/configure-common)
        (assoc
          ::tu/runner-ns runner-ns
          ::output-to output-to)
        (build-api/with-compiler-options
          {:source-map true})
        (build-api/with-build-options
          {:output-dir      output-dir
           :greedy          true
           :dynamic-resolve true})
        (build-api/with-js-options {:js-provider :shadow})
        (cond->
          js-options
          (build-api/with-js-options js-options)

          (not (get-in config [:compiler-options :output-feature-set]))
          (build-api/with-compiler-options {:output-feature-set :es8}))

        (build-api/configure-modules {:test {:entries     []
                                             :output-name (.getName output-to)}})

        ;; FIXME: maybe add devtools but given how odd karma loads js that might not be reliable
        )))

;; since :configure is only called once in :dev
;; we delay setting the :entries until compile-prepare which is called every cycle
;; need to come up with a cleaner API for this
(defn test-resolve
  [{::tu/keys [runner-ns] :as state} mode config]
  (let [test-namespaces
        (tu/find-test-namespaces state config)

        entries
        (-> '[shadow.test.env]                              ;; must be included before any deftest because of the cljs.test mod
            (cond->
              (= :dev mode)
              (into (get-in config [:devtools :preloads])))
            (into test-namespaces)
            (conj runner-ns))]

    #_(build/log state {:type            ::test-namespaces
                        :test-namespaces test-namespaces
                        :entries         entries})

    (-> state
        (assoc ::tu/test-namespaces test-namespaces)
        (assoc-in [::modules/config :test :entries] entries)
        ;; re-analyze modules since we modified the entries
        (modules/analyze)
        (tu/inject-extra-requires)
        )))

(defn flush-karma-test-file
  [{::keys [output-to] :keys [polyfill-js build-options build-sources] :as state} config]

  (let [output-dir (.getName (:output-dir build-options))

        prepend
        (str "var shadow$provide = {};\n"
             "var $jscomp = {};\n"
             (output/closure-defines-and-base state)
             (when (seq polyfill-js)
               (str "\n" polyfill-js "\n"))
             "goog.global[\"$CLJS\"] = goog.global;\n")

        resources
        (->> build-sources
             (map #(data/get-source-by-id state %))
             (remove #(= "goog/base.js" (:resource-name %))))

        filenames
        (->> resources
             (map :output-name)
             (map #(str output-dir "/files/" %))
             (into []))]

    (spit output-to prepend)
    (spit (io/file output-dir "files.json") (json/write-str filenames :escape-slash false))

    (doseq [rc resources]
      (let [{:keys [js]} (data/get-output! state rc)
            out-file (io/file output-dir "files" (:output-name rc))]
        (io/make-parents out-file)
        (spit out-file js))))
  state)

(defn flush [state mode config]
  (case mode
    :dev
    (flush-karma-test-file state config)

    :release
    (output/flush-optimized state)))

(defn process
  [{::build/keys [stage mode config] :as state}]
  (case stage
    :configure
    (configure state mode config)

    :resolve
    (test-resolve state mode config)

    :flush
    (flush state mode config)

    state
    ))