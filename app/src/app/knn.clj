(ns app.knn
  (:use [uncomplicate.neanderthal core native linalg]
        [criterium.core])
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            )
  )

;; kNN 算法伪代码：

;; 对于每一个在数据集中的数据点：
;;     计算目标的数据点（需要分类的数据点）与该数据点的距离
;;     将距离排序：从小到大
;;     选取前K个最短距离
;;     选取这K个中最多的分类类别
;;     返回该类别来作为目标数据点的预测值

(defn handwriting-class-test []
  ;; 1. 导入数据
  
  )

(defn parse-int [s]
  (Integer. (re-find  #"\d+" s )))

(defn img2vector [file]
  (let [;;file (io/resource "data/2.KNN/trainingDigits/0_0.txt")
        lines (line-seq (io/reader file))
        text (clojure.string/join lines)
        int-seq (map #(Character/digit % 10) (char-array text))
        ]
    int-seq))


(defn classify0
  "inx[1,2,3]
    DS=[[1,2,3],[1,2,0]]
    inX: 用于分类的输入向量
    dataSet: 输入的训练样本集
    labels: 标签向量
    k: 选择最近邻居的数目
    注意：labels元素数目和dataSet行数相同；程序使用欧式距离公式.

    预测数据所在分类可在输入下列命令
    kNN.classify0([0,0], group, labels, 3)
  "
  [inX dataset labels k]
  (let [;;inX [1 2 3]
        ;;dataset (dge 3 3 [1 2 3 4 5 6 7 8 9])
        ;;labels '("a" "a" "b")
        ;;k 2
        ]
    (if (and (= (count inX) (ncols dataset))
             (= (count labels) (mrows dataset)))
      (let [mat  (dge (mrows dataset)
                      (ncols dataset) (repeat inX) {:layout :row})
            diff-mat (axpy 1 mat -1 dataset)
            distances (map-indexed (fn [idx item]
                                     {:label (nth labels idx)
                                      :distance (nrm2 item)}
                                     )
                                   (rows diff-mat))
            index-distance (zipmap (range (count labels)) distances)
            sorted-index-distance (sort-by (fn [item]
                                             (:distance (val item))) index-distance)
            sorted-k-count #spy/d (take k sorted-index-distance)
            label-frequency #spy/d (frequencies (for [item sorted-k-count]
                                           (:label (val item)) 
                                           ))
            sorted-label-frequency #spy/d (into (sorted-map-by (fn [key1 key2]
                                                                 (compare [(get label-frequency key2) key2]
                                                                   [(get label-frequency key1) key1])))
                                                label-frequency)
            ]
        (first (first sorted-label-frequency)))
      (throw (ex-info "输入矩阵行数、列数不匹配"
                      {:inX (count inX)
                       :dataset-rows (mrows dataset)
                       :dataset-cols (ncols dataset)}))
      )))

(defn real-num [file]
  (let [;; file (-> "data/2.KNN/testDigits/0_0.txt"
        ;;          io/resource
        ;;          io/file)
        file-name (.getName file)
        ]
    (first (s/split file-name #"_"))))




(defn handwriting-class-test []
  (let [files (filter #(.isFile %)
                      (-> "data/2.KNN/trainingDigits/"
                          io/resource
                          io/file
                          file-seq
                          ))
        m (count files)
        n 1024
        ;;导入训练数据
        imgs-seq (for [file files]
                   (img2vector file))
        labels (for [file files]
                 (real-num file))
        dataset (dge m n imgs-seq)
        ]
    ;;导入测试数据 预测测试结果是否正确
    (let [files (filter #(.isFile %)
                        (-> "data/2.KNN/testDigits"
                            io/resource
                            io/file
                            file-seq))
          total-count (count files)
          error-count (atom 0)
          ]
      (doseq [file files]
        (let [num (real-num file)
              inX (img2vector file)
              guess-num (classify0 inX dataset labels 3)]
          (println (format "the classifier came back with: %s, the real answer is: %s, file: %s" guess-num num (.getName file)))
          (when (not= num guess-num)
            (swap! error-count inc))))
      (println (format "the total number of errors is: %d" @error-count))
      (println (format "the total error rate is: %f" (float (/ @error-count total-count))))
      )))


