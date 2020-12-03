(ns aoc-2020.day-2-test
  (:require [aoc-2020.day-2 :as day-2]
            [clojure.test :refer [deftest is]]))


(def rule-lines
  ["1-3 a: abcde"
   "1-3 b: cdefg"
   "2-9 c: ccccccccc"])

(def parsed-pw-rules
  (mapv day-2/parse-pw-rule rule-lines))


;; Part 1

(deftest pw-valid?-test
  (is (true? (day-2/pw-valid? (first parsed-pw-rules))))
  (is (false? (day-2/pw-valid? (second parsed-pw-rules)))))

(deftest valid-password-count-test
  (is (= 2 (day-2/valid-password-count parsed-pw-rules))))


;; Part 2

(deftest positional-pw-valid?-test
  (is (true? (day-2/positional-pw-valid? (first parsed-pw-rules))))
  (is (false? (day-2/positional-pw-valid? (second parsed-pw-rules))))
  (is (false? (day-2/positional-pw-valid? (last parsed-pw-rules)))))

(deftest valid-positional-password-count-test
  (is (= 1 (day-2/valid-positional-password-count parsed-pw-rules))))
