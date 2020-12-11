(ns aoc-2020.day-8-test
  (:require [aoc-2020.day-8 :as day-8]
            [clojure.test :refer [deftest is]]))

(def code-input
  "nop +0
   acc +1
   jmp +4
   acc +3
   jmp -3
   acc -99
   acc +1
   jmp -4
   acc +6")

(def parsed-code-input
  [[:nop 0]
   [:acc 1]
   [:jmp 4]
   [:acc 3]
   [:jmp -3]
   [:acc -99]
   [:acc 1]
   [:jmp -4]
   [:acc 6]])

(deftest parse-code-test
  (is (= parsed-code-input (day-8/parse-code code-input))))

(deftest execute-code-test
  (is (= 5 (day-8/execute-code parsed-code-input))))

(def code-permutations
  [[[:jmp 0]
    [:acc 1]
    [:jmp 4]
    [:acc 3]
    [:jmp -3]
    [:acc -99]
    [:acc 1]
    [:jmp -4]
    [:acc 6]]
   [[:nop 0]
    [:acc 1]
    [:nop 4]
    [:acc 3]
    [:jmp -3]
    [:acc -99]
    [:acc 1]
    [:jmp -4]
    [:acc 6]]
   [[:nop 0]
    [:acc 1]
    [:jmp 4]
    [:acc 3]
    [:nop -3]
    [:acc -99]
    [:acc 1]
    [:jmp -4]
    [:acc 6]]
   [[:nop 0]
    [:acc 1]
    [:jmp 4]
    [:acc 3]
    [:jmp -3]
    [:acc -99]
    [:acc 1]
    [:nop -4]
    [:acc 6]]])

(deftest jmp-nop-permutations-test
  (is (= code-permutations (vec (day-8/jmp-nop-permutations parsed-code-input)))))
