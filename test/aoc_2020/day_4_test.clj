(ns aoc-2020.day-4-test
  (:require [aoc-2020.day-4 :as day-4]
            [clojure.test :refer [deftest is]]))


(def batch-input
  "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
   byr:1937 iyr:2017 cid:147 hgt:183cm

   iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
   hcl:#cfa07d byr:1929

   hcl:#ae17e1 iyr:2013
   eyr:2024
   ecl:brn pid:760753108 byr:1931
   hgt:179cm

   hcl:#cfa07d eyr:2025 pid:166559648
   iyr:2011 ecl:brn hgt:59in")

(def parsed-batch-input
  [{:ecl "gry"
    :pid "860033327"
    :eyr 2020
    :hcl "#fffffd"
    :byr 1937
    :iyr 2017
    :cid "147"
    :hgt {:height 183
          :unit   :cm}}
   {:iyr 2013
    :ecl "amb"
    :cid "350"
    :eyr 2023
    :pid "028048884"
    :hcl "#cfa07d"
    :byr 1929}
   {:hcl "#ae17e1"
    :iyr 2013
    :eyr 2024
    :ecl "brn"
    :pid "760753108"
    :byr 1931
    :hgt {:height 179
          :unit   :cm}}
   {:hcl "#cfa07d"
    :eyr 2025
    :pid "166559648"
    :iyr 2011
    :ecl "brn"
    :hgt {:height 59
          :unit   :in}}])

(deftest parse-input-test
  (is (= parsed-batch-input (day-4/parse-batch batch-input))))

;; Part 1

(deftest valid-untyped-entry?-test
  (let [[entry-1 entry-2 entry-3 entry-4] parsed-batch-input]
    (is (true? (day-4/valid-untyped-entry? entry-1)))
    (is (false? (day-4/valid-untyped-entry? entry-2)))
    (is (true? (day-4/valid-untyped-entry? entry-3)))
    (is (false? (day-4/valid-untyped-entry? entry-4)))))

;; Part 2

(def parsed-invalid-batch
  (day-4/parse-batch
   "eyr:1972 cid:100
    hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

    iyr:2019
    hcl:#602927 eyr:1967 hgt:170cm
    ecl:grn pid:012533040 byr:1946

    hcl:dab227 iyr:2012
    ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

    hgt:59cm ecl:zzz
    eyr:2038 hcl:74454a iyr:2023
    pid:3556412378 byr:2007"))

(def parsed-valid-batch
  (day-4/parse-batch
   "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
    hcl:#623a2f

    eyr:2029 ecl:blu cid:129 byr:1989
    iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

    hcl:#888785
    hgt:164cm byr:2001 iyr:2015 cid:88
    pid:545766238 ecl:hzl
    eyr:2022

    iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719"))

(deftest valid-typed-entry?-test
  (is (every? (complement day-4/valid-typed-entry?) parsed-invalid-batch))
  (is (every? day-4/valid-typed-entry? parsed-valid-batch)))
