(ns aoc-2020.day-8
  (:require [clojure.java.io :as io]
            [cuerdas.core :as str]))


(def code-input
  (slurp (io/resource "day_8_input.txt")))

(def line-regex
  #"^\s*(.*?)[\s\+]+([\d\-]+\s*$)")

(defn parse-instruction
  [code-line]
  (let [[_ op arg] (re-find line-regex code-line)]
    (vector
     (keyword op)
     (str/parse-long arg))))

(defn parse-code
  [code]
  (->> (str/lines code)
       (mapv parse-instruction)))

(def parsed-code-input
  (parse-code code-input))

(defn execute-code
  [parsed-code]
  (let [code-length (count parsed-code)]
    (loop [iptr      0
           executed  #{}
           acc-value 0]
      (cond
        ;; Instruction pointer is one past end, successful termination
        (= iptr code-length)
        [:terminated acc-value]

        ;; Instruction pointer is out of bounds
        (> iptr code-length)
        [:iptr-oob acc-value]

        ;; Instruction has been executed, loop detected
        (contains? executed iptr)
        [:loop-detected acc-value]

        ;; Instruction pointer is valid
        :else
        (let [[op arg] (nth parsed-code iptr)]
          (case op
            :nop (recur (inc iptr) (conj executed iptr) acc-value)
            :acc (recur (inc iptr) (conj executed iptr) (+ acc-value arg))
            :jmp (recur (+ iptr arg) (conj executed iptr) acc-value)))))))

;; Part 1

(defn calculate-value-before-loop
  [parsed-code]
  (let [[_ value] (execute-code parsed-code)]
    value))


;; Part 2

(defn jmp-nop-permutations
  [parsed-code]
  (for [i (range (count parsed-code))
        :let
        [[op arg] (nth parsed-code i)]
        :when
        (contains? #{:jmp :nop} op)
        :let
        [swapped (if (= :nop op) :jmp :nop)]]
    (assoc parsed-code i [swapped arg])))

(defn terminated?
  [execution-result]
  (when (= :terminated (first execution-result))
    (second execution-result)))

(defn calculate-fixed-code-value
  [parsed-code]
  (->> parsed-code
       (jmp-nop-permutations)
       (map execute-code)
       (some terminated?)))
