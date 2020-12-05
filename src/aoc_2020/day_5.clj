(ns aoc-2020.day-5
  (:require [clojure.java.io :as io]
            [clojure.set :as set]
            [cuerdas.core :as str]))

(def passes-input
  (slurp (io/resource "day_5_input.txt")))

(defn parse-passes
  [passes-input]
  (->> (str/lines passes-input)
       (mapv str/trim)))

(def parsed-passes-input
  (str/lines passes-input))

(defn build-bsp-decoder
  [left-char right-char bsp-max]
  (fn [bsp-str]
    (let [split-fn (fn [[left right]]
                     (let [mid (int (/ (+ left (inc right)) 2))]
                       [[left (dec mid)] [mid right]]))]
      (loop [bsp-chars            (seq bsp-str)
             [bsp-left bsp-right] (split-fn [0 (dec bsp-max)])]
        (let [[bsp-char & rest-chars] bsp-chars]
          (if (seq rest-chars)
            (condp = bsp-char
              left-char  (recur (rest bsp-chars) (split-fn bsp-left))
              right-char (recur (rest bsp-chars) (split-fn bsp-right)))
            (condp = bsp-char
              left-char  (first bsp-left)
              right-char (first bsp-right))))))))

(def decode-row
  (build-bsp-decoder \F \B 128))

(def decode-column
  (build-bsp-decoder \L \R 8))

(defn calculate-seat-id
  [row-num column-num]
  (+ (* 8 row-num) column-num))

(defn decode-pass
  [pass]
  (let [row        (subs pass 0 7)
        row-num    (decode-row row)
        column     (subs pass 7)
        column-num (decode-column column)
        seat-id    (calculate-seat-id row-num column-num)]
    {:seat-position [row-num column-num]
     :seat-id       seat-id}))

(def decoded-passes-input
  (mapv decode-pass parsed-passes-input))

;; Part 1

(defn calculate-highest-seat-id
  [decoded-passes]
  (apply max (mapv :seat-id decoded-passes)))


;; Part 2

(defn shrink-front-rows
  [missing-seats]
  (loop [actually-missing missing-seats
         frontmost-row    0]
    (let [[frontmost-seats rest-seats] (split-with
                                        #(= frontmost-row (first %))
                                        actually-missing)]
      (if (seq frontmost-seats)
        (recur rest-seats (inc frontmost-row))
        rest-seats))))

(defn shrink-back-rows
  [missing-seats number-of-rows]
  (loop [actually-missing (reverse missing-seats)
         backmost-row     (dec number-of-rows)]
    (let [[backmost-seats rest-seats] (split-with
                                       #(= backmost-row (first %))
                                       actually-missing)]
      (if (seq backmost-seats)
        (recur rest-seats (dec backmost-row))
        rest-seats))))

(defn missing-seat-positions
  [decoded-passes number-of-rows number-of-columns]
  (let [total-seat-set   (set (for [row    (range number-of-rows)
                                    column (range number-of-columns)]
                                [row column]))
        present-seat-set (set (map :seat-position decoded-passes))
        missing-seats    (sort (set/difference total-seat-set present-seat-set))]
    (-> (shrink-front-rows missing-seats)
        (shrink-back-rows number-of-rows)
        (set))))

(defn calculate-your-seat-id
  [decoded-passes]
  (let [missing-seats (missing-seat-positions decoded-passes 128 8)]
    (assert (= 1 (count missing-seats)) "More than one legit missing seat")
    (apply calculate-seat-id (first missing-seats))))
