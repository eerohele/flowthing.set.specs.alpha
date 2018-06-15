(ns flowthing.set.specs.alpha-test
  (:require [clojure.set :as set]
            [clojure.spec.test.alpha :as spec.test]
            [clojure.test :refer [deftest is use-fixtures]]
            [expound.alpha :as expound]
            [flowthing.set.specs.alpha])
  (:import (clojure.lang ExceptionInfo)))

(use-fixtures :once (fn [f]
                      (spec.test/instrument)
                      (f)
                      (spec.test/unstrument)))

(deftest union
  (is (= #{1 2 3} (set/union #{1 2} #{3})))
  (is (thrown? ExceptionInfo (set/union #{1 2} [3]))))

(deftest intersection
  (is (= #{1} (set/intersection #{1 2} #{1})))
  (is (thrown? ExceptionInfo (set/intersection #{1 2} [1]))))

(deftest difference
  (is (= #{2} (set/difference #{1 2} #{1})))
  (is (thrown? ExceptionInfo (set/difference #{1 2} [1]))))

(deftest select
  (is (= #{1 2} (set/select int? #{1 2 :a})))
  (is (thrown? ExceptionInfo (set/select int #{1 2 :a}))))

(deftest project
  (is (= #{{:a 1}} (set/project [{:a 1 :b 2}] [:a])))
  (is (= #{{:a 1}} (set/project #{{:a 1 :b 2}} [:a])))
  (is (thrown? ExceptionInfo (set/project #{{:a 1 :b 2}} {:a nil}))))

(deftest rename-keys
  (is (= {:c 1 :d 2} (set/rename-keys {:a 1 :b 2} {:a :c :b :d})))
  (is (thrown? ExceptionInfo (set/rename-keys {:a 1 :b 2} [:a :c :b :d]))))

(deftest rename
  (is (= #{{:b 1 :c 1} {:b 2 :c 2}} (set/rename #{{:a 1 :b 1} {:a 2 :b 2}} {:a :c})))
  (is (= #{{:b 1 :c 1} {:b 2 :c 2}} (set/rename [{:a 1 :b 1} {:a 2 :b 2}] {:a :c})))
  (is (thrown? ExceptionInfo (set/rename [{:a 1 :b 1} {:a 2 :b 2}] [:a :c]))))

(deftest index
  (is (= {{:a 1} #{{:a 1 :b 2}}} (set/index #{{:a 1 :b 2}} [:a])))
  (is (= {{:a 1} #{{:a 1 :b 2}}} (set/index [{:a 1 :b 2}] [:a])))
  (is (thrown? ExceptionInfo (set/index [{:a 1 :b 2}] :a))))

(deftest map-invert
  (is (= {:b :a} (set/map-invert {:a :b})))
  (is (thrown? ExceptionInfo (set/map-invert [:a :b]))))

(deftest join
  (is (= #{{:a 1 :b 1} {:a 2 :b 2}} (set/join #{{:a 1} {:a 2}} #{{:a 1 :b 1} {:a 2 :b 2}})))
  (is (= #{{:a 1 :b 1} {:a 2 :b 2}} (set/join #{{:a 1} {:a 2}} [{:a 1 :b 1} {:a 2 :b 2}])))
  (is (= #{{:a 1 :b 1} {:a 2 :b 2}} (set/join [{:a 1} {:a 2}] #{{:a 1 :b 1} {:a 2 :b 2}})))
  (is (= #{{:a 1 :b 1} {:a 2 :b 2}} (set/join [{:a 1} {:a 2}] [{:a 1 :b 1} {:a 2 :b 2}])))
  (is (= #{{:a 1, :b 1} {:a 2, :b 2}}) (set/join #{{:a 1} {:a 2}} #{{:b 1} {:b 2}} {:a :b}))
  (is (= #{{:a 1, :b 1} {:a 2, :b 2}}) (set/join #{{:a 1} {:a 2}} [{:b 1} {:b 2}] {:a :b}))
  (is (= #{{:a 1, :b 1} {:a 2, :b 2}}) (set/join [{:a 1} {:a 2}] #{{:b 1} {:b 2}} {:a :b}))
  (is (= #{{:a 1, :b 1} {:a 2, :b 2}}) (set/join [{:a 1} {:a 2}] [{:b 1} {:b 2}] {:a :b}))
  (is (thrown? ExceptionInfo (set/join [{:a 1} {:a 2}] [{:b 1} {:b 2}] [:a :b]))))

(deftest subset?
  (is (true? (set/subset? #{:a} #{:a :b})))
  (is (thrown? ExceptionInfo (set/subset? [:a] [:a :b]))))

(deftest superset?
  (is (true? (set/superset? #{:a :b} #{:a})))
  (is (thrown? ExceptionInfo (set/superset? [:a :b] [:a]))))

(defn- ns-syms
  [ns]
  (->> ns (ns-publics) (keys) (map #(symbol (str ns) (str %)))))

(deftest check
  (let [opts {:clojure.spec.test.check/opts {:num-tests 50}}
        results (spec.test/check (ns-syms 'clojure.set) opts)]
    (is (not (contains? (::spec.test/ret results) :fail))
        (expound/explain-results results))))
