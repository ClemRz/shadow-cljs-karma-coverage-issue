(ns coverage.app-test
  (:require [cljs.test :refer-macros [deftest is]]
            [coverage.app :as app]))

(deftest some-function-test
         (is (= "foo" (app/some-function nil)))
         (is (= "bar" (app/some-function :something))))
