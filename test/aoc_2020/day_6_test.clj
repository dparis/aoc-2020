(ns aoc-2020.day-6-test
  (:require [aoc-2020.day-6 :as day-6]
            [clojure.test :refer [deftest is]]
            [cuerdas.core :as str]))


(def form-groups-input
  "abc

   a
   b
   c

   ab
   ac

   a
   a
   a
   a

   b")

(def parsed-form-groups-input
  [["abc"]
   ["a" "b" "c"]
   ["ab" "ac"]
   ["a" "a" "a" "a"]
   ["b"]])

(deftest parse-form-groups-test
  (is (= parsed-form-groups-input (day-6/parse-form-groups form-groups-input))))

(def answer-sets
  [#{"a" "b" "c"}
   #{"a" "b" "c"}
   #{"a" "b" "c"}
   #{"a"}
   #{"b"}])

(deftest form-group-answer-set
  (is (= answer-sets (map day-6/form-group-answer-set parsed-form-groups-input))))

(def unanimous-answers
  [{"a" 1, "b" 1, "c" 1}
   {}
   {"a" 2}
   {"a" 4}
   {"b" 1}])

(deftest form-group-unanimous-answers-test
  (is (= unanimous-answers (map day-6/form-group-unanimous-answers parsed-form-groups-input))))
