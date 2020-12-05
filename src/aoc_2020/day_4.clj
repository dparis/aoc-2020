(ns aoc-2020.day-4
  (:require [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [cuerdas.core :as str]))


(def batch-input
  (slurp (io/resource "day_4_input.txt")))

(defmulti parse-field-value
  (fn [[field-key field-val]]
    (cond
      (contains? #{"byr" "iyr" "eyr"} field-key) :year
      (= "hgt" field-key)                        :height
      :else                                      :string)))

(defmethod parse-field-value :year
  [[_ field-val]]
  (try
    (Integer. field-val)
    (catch Exception ex)))

(def height-regex
  #"(\d+)(cm|in)")

(defmethod parse-field-value :height
  [[_ field-val]]
  (try
    (let [[_ height unit] (re-find height-regex field-val)
          height-int      (Integer. height)]
      (case unit
        "in"  {:height height-int
               :unit   :in}
        "cm"  {:height height-int
               :unit   :cm}
        :else nil))
    (catch Exception ex)))

(defmethod parse-field-value :string
  [[_ field-val]]
  field-val)

(defn parse-entry
  [batch-entry]
  (let [fields (str/split batch-entry #"\s+")]
    (reduce
     (fn [entry field]
       (let [field-tuple (str/split field ":")]
         (assoc entry
                (keyword (first field-tuple))
                (parse-field-value field-tuple))))
     {}
     fields)))

(defn parse-batch
  [batch]
  (let [entries (str/split batch #"\s*\n{2}+\s*")]
    (mapv parse-entry entries)))

(def parsed-batch-input
  (parse-batch batch-input))


;;;;;;;

(s/def :untyped/byr any?)
(s/def :untyped/iyr any?)
(s/def :untyped/eyr any?)
(s/def :untyped/hgt any?)
(s/def :untyped/hcl any?)
(s/def :untyped/ecl any?)
(s/def :untyped/pid any?)
(s/def :untyped/cid any?)

(s/def ::untyped-entry
  (s/keys :req-un [:untyped/byr :untyped/iyr :untyped/eyr :untyped/hgt
                   :untyped/hcl :untyped/ecl :untyped/pid]
          :opt-un [:untyped/cid]))

(defn valid-untyped-entry?
  [entry]
  (s/valid? ::untyped-entry entry))

;;;;;;;

(s/def :typed/byr (s/int-in 1920 2003))
(s/def :typed/iyr (s/int-in 2010 2021))
(s/def :typed/eyr (s/int-in 2020 2031))

(s/def ::unit #{:cm :in})

(s/def :cm/height (s/int-in 150 194))
(s/def :in/height (s/int-in 59 77))

(defmulti hgt-type :unit)

(defmethod hgt-type :cm
  [_]
  (s/keys :req-un [:cm/height ::unit]))

(defmethod hgt-type :in
  [_]
  (s/keys :req-un [:in/height ::unit]))

(s/def :typed/hgt (s/multi-spec hgt-type :unit))

(s/def :typed/hcl (s/and string? #(re-find #"^\#[0-9a-f]{6}$" %)))
(s/def :typed/ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})
(s/def :typed/pid (s/and string? #(re-find #"^\d{9}$" %)))
(s/def :typed/cid (s/nilable string?))

(s/def ::typed-entry
  (s/keys :req-un [:typed/byr :typed/iyr :typed/eyr :typed/hgt
                   :typed/hcl :typed/ecl :typed/pid]
          :opt-un [:typed/cid]))

(defn valid-typed-entry?
  [entry]
  (s/valid? ::typed-entry entry))


;; Part 1

(defn calculate-valid-entries-1
  [parsed-batch]
  (count (filter valid-untyped-entry? parsed-batch)))


;; Part 2

(defn calculate-valid-entries-2
  [parsed-batch]
  (count (filter valid-typed-entry? parsed-batch)))
