(ns app.mlfoundation
  (:use [uncomplicate.neanderthal core native linalg])
  (:require [uncomplicate.neanderthal.block :refer [buffer]]
            [uncomplicate.commons.core :refer [with-release]])
  )

(def x (dv 1 2 3))
(def y (dv 10 20 30))
(dot x y)

(def a (dge 3 2 [1 2 3 4 5 6]))
(def b (dge 2 3 [10 20 30 40 50 60]))
(def c (dge 3 2 [2 3 4 5 6 7]))

(mm a b)

;;矩阵减法
(axpy 1 a  -1 c)

;;元素大小
(dim a)
(dim b)

;;对角线
(dia a)
(dia b)

;;Returns a lazy sequence of diagonal vectors of the matrix a.
(dias a)

;;获取一个元素
(entry x 0)
(entry a 1 1)

;;对角优势矩阵 diagonally dominant matrix
;; 一个矩阵，如果其每一行的非对角元的模之和都小于这一行的对角元的模，即称该矩阵是严格对角优势或强对角优势（ strictly diagonally dominant ）的。

;; 若矩阵仅满足每一行的非对角元的模之和都小于等于这一行的对角元的模，但至少有一行的非对角元的模之和严格小于这一行的对角元的模，则称改矩阵是弱对角优势的。

;; 这类矩阵有着广泛的实际背景，如很多微分方程边值问题的离散化方程的系
;; 数矩阵往往具有上面的性质，因此对这类矩阵的研究是十分重要的。这类矩
;; 阵还有一些重要性质,例如，若矩阵A是严格对角优势或不可约弱对角优势的，
;; 则 A是非奇异的；若A还是埃尔米特矩阵，且对角元皆为正数，则A是正定的。
;; 又如用直接法或迭代法解系数矩阵为对角优势矩阵的线性代数方程组时，可
;; 以保证算法的稳定性或收敛性。
;;(dt )

;;线性代数中带状矩阵是什么？（band matrix）
;;https://zhidao.baidu.com/question/243920459.html
;;(gb)

;; 对角矩阵（英语：diagonal matrix）是一个主对角线之外的元素皆为0的矩
;; 阵。对角线上的元素可以为0或其他值。
;;(gd)

;;在数值分析中，是其元素大部分为零的矩阵。反之，如果大部分元素都非零，
;;则这个矩阵是稠密的
;;dense matrix 
;;(ge)

;;https://blog.csdn.net/cainv89/article/details/51763812
;;特殊矩阵——三对角矩阵(Tridiagonal Matrix)
;;(gt)

;;The index of the first entry of vector x that has the largest
;;absolute value.
;;绝对值最大的index of vector
(iamax x)
(iamax (dv 1 -3 2))

(iamin (dv 1 -3 2))
(imax (dv 1 -3 2))
(imin (dv 1 -3 2))

(matrix-type a)

;;Returns the number of rows of the matrix a.
(mrows a)


;;Matrix-vector multiplication
;;(mv a x)

;;(native)
(let [v (fv [1 2 3])] (identical? (native v) v))

;;Returns the number of columns of the matrix a.
(ncols a)

;;1-norm of vector or matrix
;;https://zhuanlan.zhihu.com/p/35897775
;;向量/矩阵范数
(nrm1 x) ;;即向量元素绝对值之和，x 到零点的曼哈顿距离

(nrm2 x) ;;2-范数也称为Euclid范数（欧几里得范数，常用计算向量长度），即向量元素绝对值的平方和再开方，表示x到零点的欧式距离
(Math/pow (+ 1 4 9) 0.5)

(nrm2 a)
(Math/pow (+ 1 4 9 16 25 36) 0.5)

(nrmi x) ;;当p趋向于正无穷时，即所有向量元素绝对值中的最大值

;;raw Returns an uninitialized instance of the same type and
;;dimension(s) as x.
(raw x)
(raw a)

;;https://zhuanlan.zhihu.com/p/27742729
;;特征值分解（EVD）的rank 1 update
;;(rk)

;;rot!
;;向量旋转

;;rotg!
;;rotm!
;;rotmg!

;;Returns the i-th row of the matrix a as a vector.
(row a 2)

;;Returns a lazy sequence of row vectors of the matrix a.
(rows a)


;;sb Creates a symmetric banded matrix (SB) in the context of factory
;;对称带状矩阵

;;scal Multiplies all entries of a copy a vector or matrix x by scalar
;;alpha.
(scal 2 x)

;;sp Creates a symmetric packed matrix (SP) in the context of factory


;;st Creates a symmetric tridiagonal matrix (ST) in the context of
;;factory

;; subband Returns a part of the banded matrix a starting from row 0,
;; column 0, that has kl subdiagonals and ku superdiagonals.


;;submatrix Returns a submatrix of the matrix a starting from row i,
;;column j, that has k rows and l columns.

;;subvector Returns a subvector starting witk k, l entries long, which
;;is a part of the neanderthal vector x.

;;Sums values of entries of a vector or matrix x.
(sum (dv -1 2 -3)) ;;=> -2.0

;;swp! Swaps all entries of vectors or matrices x and y.

;;sy Creates a dense symmetric matrix (SY)

;;Tests if x is a symmetric matrix of any kind.
(symmetric? a)
(symmetric? b)

;;tb Creates a triangular banded matrix (SB) in the context of
;;factory, with n rows and columns, k sub (lower) or super (upper)
;;diagonals.

;;tp Creates a triangular packed matrix (TP) in the context of
;;factory, with n rows and columns.

;;tr Creates a dense triangular matrix (TR) in the context of factory,
;;with n rows and n columns.

;;Transposes matrix a, i.e returns a matrix that has m’s columns as rows.
(trans a)

;;transfer Transfers the data to the memory context defined by factory
;;(native, OpenCL, CUDA, etc.).
;;https://www.zhihu.com/question/19780484

;;triangular? Tests if x is a triangular matrix of any kind.
(triangular? a)

(vctr (factory-by-type :float) 3)
(vctr (factory-by-type :float) 1 2 3)



(view (buffer (vctr (factory-by-type :float) 1 2 3)))

(view-ge a)
(view-ge (tr (factory-by-type :float) 3 (range 6)))
(view-sy (tr (factory-by-type :float) 3 (range 6)))

(vspace? x)
(vspace? a)


(def rand-mat (dge 4 4 (repeatedly 16 rand)))
(def inv-rand-mat (tri (trf rand-mat)))
(def tra-rand-mat (trans rand-mat))
(mm  inv-rand-mat rand-mat)



;;https://dragan.rocks/articles/19/Deep-Learning-in-Clojure-From-Scratch-to-GPU-1-Representing-Layers-and-Connections

(with-release [x (dv 0.3 0.9)
               w1 (dge 4 2 [0.3 0.6
                            0.1 2.0
                            0.9 3.7
                            0.0 1.0]
                       {:layout :row})
               h1 (dv 4)]
  (println (mv! w1 x h1)))


(with-release [x (dv 0.3 0.9)
               w1 (dge 4 2 [0.3 0.6
                            0.1 2.0
                            0.9 3.7
                            0.0 1.0]
                       {:layout :row})
               h1 (dv 4)
               w2 (dge 1 4 [0.75 0.15 0.22 0.33])
               y (dv 1)]
  (println (mv! w2 (mv! w1 x h1) y)))

;;The Vector Space Rn
;;https://dragan.rocks/articles/17/Clojure-Linear-Algebra-Refresher-Vector-Spaces#orgeb98be2
(def v1 (dv -1 2 5.2 0))
(def v2 (dv (range 22)))
(def v3 (dv -2 -3 1 0))
;;add vector
;;This won't work, since we cannot add two vectors from different
;vector spaces (R4 and R22)
;;(xpy v1 v2)

(xpy v1 v3)

;;https://www.cnblogs.com/pinard/p/6251584.html
;; 对于奇异值,它跟我们特征分解中的特征值类似，在奇异值矩阵中也是按照从
;; 大到小排列，而且奇异值的减少特别的快，在很多情况下，前10%甚至1%的奇
;; 异值的和就占了全部的奇异值之和的99%以上的比例。也就是说，我们也可以
;; 用最大的k个的奇异值和对应的左右奇异向量来近似描述矩阵。

;; 主成分分析（PCA）原理总结中
;; 由于这个重要的性质，SVD可以用于PCA降维，来做数据压缩和去噪。也可以
;; 用于推荐算法，将用户和喜好对应的矩阵做特征分解，进而得到隐含的用户
;; 需求来做推荐。


(let [a (dge 4 4 [1 0 0 0
                  2 0 0 0
                  0 1 0 0
                  0 0 1 0])]
  (svd a))
