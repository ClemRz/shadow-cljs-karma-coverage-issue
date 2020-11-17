(defproject shadow-cljs-karma-coverage-issue "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.11.4"]]

  :plugins [[lein-shadow "0.3.1"]
            [lein-shell "0.5.0"]]

  :min-lein-version "2.9.0"

  :source-paths ["src/main"]

  :test-paths ["src/test"]

  :clean-targets ^{:protect false} ["target"])
