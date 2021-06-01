(ns finance.util)

#?(:cljs (def BigNumber (js/require "bignumber.js")))

(def ^:private mill (let [cents 100 mil 1000] (* cents mil)))

(defn to-cents
  [num]
  (if-not (number? num) 
    num
    #?(:clj  (/ num mill)
       :cljs (.div (BigNumber. num) (BigNumber. mill)))))

(defn to-mills
  [num]
  (if-not (number? num) 
    num
    #?(:clj  (* num mill)
       :cljs (.times (BigNumber. num) (BigNumber. mill)))))


; --- safe math ---
; @docs: https://mikemcl.github.io/bignumber.js

(defn add
  ([a b]
  #?(:clj  (+ a b)
     :cljs (.toNumber (.plus (BigNumber. a) (BigNumber. b)))))
  ([a b c]
  #?(:clj  (+ a b c)
     :cljs (.toNumber (.plus (BigNumber. a) (BigNumber. b) (BigNumber. c))))))

(defn minus
  [a b]
  #?(:clj  (- a b)
     :cljs (.toNumber (.minus (BigNumber. a) (BigNumber. b)))))

(defn multiply
  [a b]
  #?(:clj  (/ a b)
     :cljs (.toNumber (.times (BigNumber. a) (BigNumber. b)))))

(defn divide
  [a b]
  #?(:clj  (/ a b)
     :cljs (.toNumber (.div (BigNumber. a) (BigNumber. b)))))
