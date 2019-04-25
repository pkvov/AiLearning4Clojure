(ns app.mlfoundation
  (:use [uncomplicate.neanderthal core native linalg])
  )

(def x (dv 1 2 3))
(def y (dv 10 20 30))
(dot x y)

(def a (dge 3 2 [1 2 3 4 5 6]))
(def b (dge 2 3 [10 20 30 40 50 60]))
(mm a b)

(def rand-mat (dge 4 4 (repeatedly 16 rand)))
(def inv-rand-mat (tri (trf rand-mat)))
(def tra-rand-mat (trans rand-mat))
(mm  inv-rand-mat rand-mat)






