(ns aoc-2020.day-9-test
  (:require [aoc-2020.day-9 :as day-9]
            [clojure.test :refer [deftest is]]))


(def data-input
  "35
   20
   15
   25
   47
   40
   62
   55
   65
   95
   102
   117
   150
   182
   127
   219
   299
   277
   309
   576")

(def parsed-data
  [35 20 15 25 47 40 62 55 65 95 102 117 150 182 127 219 299 277 309 576])

(deftest parse-data-test
  (is (= parsed-data (day-9/parse-data data-input))))

(deftest valid-at-offset?-test
  (is (true? (day-9/valid-at-index? parsed-data 5 5)))
  (is (false? (day-9/valid-at-index? parsed-data 5 14))))

(deftest first-invalid-value-test
  (is (= 127 (day-9/first-invalid-value parsed-data 5))))

(deftest encryption-weakness-test
  (is (= 62 (day-9/encryption-weakness parsed-data 5))))
