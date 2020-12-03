(ns aoc-2020.day-2
  (:require [clojure.java.io :as io]
            [cuerdas.core :as str]))

(def input
  (slurp (io/resource "day_2_input.txt")))

(def pw-line-regex
  #"(\d+)-(\d+)\s+(\w):\s+(\w+)")

(defn parse-pw-rule
  [line]
  (let [[_ min-count max-count rule-char password] (re-find pw-line-regex line)]
    {:min-count (Integer. min-count)
     :max-count (Integer. max-count)
     :char      (first rule-char)
     :password  password}))

(def parsed-pw-rules
  (->> input
       (str/lines)
       (map parse-pw-rule)))

;; Part 1

(defn pw-valid?
  [pw-rule]
  (let [char-freqs (frequencies (:password pw-rule))
        char-count (get char-freqs (:char pw-rule))]
    (boolean
     (when char-count
       (<= (:min-count pw-rule) char-count (:max-count pw-rule))))))

(defn valid-password-count
  [pw-rules]
  (count (filter pw-valid? pw-rules)))


;; Part 2

(defmacro xor
  [x y]
  `(cond
     (and ~x ~y) false
     ~x          true
     ~y          true
     :else       false)) 

(defn password-char-at-pos
  [password n]
  (nth password (dec n)))

(defn positional-pw-valid?
  [pw-rule]
  (let [password        (:password pw-rule)
        rule-char       (:char pw-rule)
        first-pos-char  (password-char-at-pos password (:min-count pw-rule))
        second-pos-char (password-char-at-pos password (:max-count pw-rule))]
    (xor (= rule-char first-pos-char)
         (= rule-char second-pos-char))))

(defn valid-positional-password-count
  [pw-rules]
  (count (filter positional-pw-valid? pw-rules)))
