# 作业2： 服务QoS预测
访问 WS-DREAM(https://github.com/wsdream/WS-DREAM)，使用至少五种方法进行 QoS 预测并说明每种方法的原理。

## 数据
使用WS-DREAM中的dataset2进行试验，因为相比dataset1，数据格式更加整齐，数据也更多。

## 方法

1. Umean

    使用同一个用户的所有其他服务的QoS值来预测当前目标服务的QoS值

2. Smean

    使用同一种服务的所有其他用户的Qos值来预测当前用户对于该服务的QoS值

3. User-based collaborative filtering (UCF)
   
    对于所有用户使用各种计算的历史记录构造 $N \times M$ 特征矩阵$A$, 其中$N$为用户的数量， $M$为所有可用服务的数量， 矩阵中的元素$A[n][m]$表示第n个用户使用第m个服务时的QoS值。 对于待预测的用户$u$， 我们可以根据矩阵$A$获得其特征向量$A[u]$,通过和其他用户计算cosine距离，找到最相似的$k$个用户，用这些用户对于服务$s$的QoS值的平均作为用户$u$使用服务$s$的Qos值的预测。

4. Service-based collaborative filtering (SCF)
   
   对于所有用户使用各种计算的历史记录构造 $M \times N$ 特征矩阵$B$, 其中$N$为用户的数量， $M$为所有可用服务的数量， 矩阵中的元素$B[m][n]$表示第n个用户使用第m个服务时的QoS值。 对于待预测的服务$s$， 我们可以根据矩阵$A$获得其特征向量$B[s]$,通过和其他服务计算cosine距离，找到最相似的$k$个服务，使用对于这些服务的其他用户的Qos值来预测当前用户$u$使用服务$s$的Qos值。

5. Hybrid collaborative filtering (HCF)
   
   结合方法4和方法5， 同时构造特征矩阵$A、B$, 然后分别根据当前用户的特征向量和当前服务的特征向量寻找相似的向量，通过两种方式查找的相似向量进行平均，作为对于当前用户使用当前服务的QoS值的预测。

## 代码

所有代码及运行结果可查看 [algorithms.ipynb](algorithms.ipynb)
1. Umean
   ```python
    Users = {}
    all_scores = []
    for line in tqdm(train_data_5):
        user_id = line[0]
        rt  = line[-1]
        if user_id in Users:
            Users[user_id].append(rt)
        else:
            Users[user_id] = [rt]
        all_scores.append(rt)

    search_table = {}
    for k in Users.keys():
        search_table[k] = sum(Users[k])*1.000 / len(Users[k])

    avg_score = sum(all_scores)*1.000 / len(all_scores)

    ## Evaluate
    avg_mse = 0

    for line in tqdm(dev_data):
        user_id = line[0]
        y_true = line[-1]
        if user_id in search_table:
            y_pred = search_table[user_id]
        else:
            y_pred = avg_score
        mse_score = MAE(y_true, y_pred)
        avg_mse += mse_score

    avg_mse = avg_mse / len(dev_data)

   ```
2. Smean
   ```python
    Items = {}
    all_scores = []
    for line in tqdm(train_data_5):
        item_id = line[1]
        rt = line[-1]
        if item_id in Items:
            Items[item_id].append(rt)
        else:
            Items[item_id] = [rt]
        all_scores.append(rt)

    search_table = {}

    for k in Items.keys():
        search_table[k] = sum(Items[k])*1.000 / len(Items[k])
    avg_score = sum(all_scores)*1.000 / len(all_scores)

    ## Evaluate
    avg_mse = 0
    for line in tqdm(dev_data):
        item_id = line[1]
        y_true = line[-1]
        if item_id in search_table:
            y_pred = search_table[item_id]
        else:
            y_pred = avg_score
        mse_score = MAE(y_true, y_pred)
        avg_mse += mse_score

    avg_mse = avg_mse / len(dev_data)
   ```
   
3. User-based collaborative filtering
   ```python
    import numpy as np
    from scipy.spatial import distance

    top_k = 5
    Users = np.zeros((142, 4500))
    for line in tqdm(train_data_5):
        user_id = int(line[0])
        item_id = int(line[1])
        val = line[-1]
        Users[user_id][item_id] = val

    # Evaluating
    avg_mse = 0.0
    all_true = []
    all_pred = []
    for line in tqdm(dev_data):
        user_id = int(line[0])
        item_id = int(line[1])
        y_true = line[-1]
        user_vec =Users[user_id]
        all_dis = [distance.cosine(user_vec, x) for x in Users]
        top_k_vec = np.argsort(np.array(all_dis))[1:6]
        all_true.append(y_true)
        ref_item = [Users[x][item_id] for x in top_k_vec]
        y_pred = sum(ref_item) /  len(ref_item)
        all_pred.append(y_pred)

    for t, p in zip(all_true, all_pred):
        avg_mse += MAE(y_true, y_pred)

    avg_mse = avg_mse /len(all_true)
   ```
4. Service-based collaborative filtering
   ```python
    import numpy as np
    from scipy.spatial import distance
    from multiprocessing import Pool

    top_k = 5
    Items = np.zeros((4500, 142))
    for line in tqdm(train_data_5):
        user_id = int(line[0])
        item_id = int(line[1])
        val = line[-1]
        Items[item_id][user_id] = val

    # Evaluating
    avg_mse = 0.0
    all_true = []
    all_pred = []
    for line in tqdm(dev_data):
        user_id = int(line[0])
        item_id = int(line[1])
        y_true = line[-1]
        item_vec = Items[item_id]
    #     def f(y):
    #         return distance.cosine(item_vec, y)
    #     with Pool(64) as p:
    #         all_dis = p.map(f, Items)
        all_dis = [distance.cosine(item_vec, x) for x in Items] 
        top_k_vec = np.argsort(np.array(all_dis))[1:6]
        all_true.append(y_true)
        ref_User = [Items[x][user_id] for x in top_k_vec]
        y_pred = sum(ref_item) /  len(ref_item)
        all_pred.append(y_pred)

    for t, p in zip(all_true, all_pred):
        avg_mse += MAE(y_true, y_pred)

    avg_mse = avg_mse /len(all_true)
   ```
5. Hybrid collaborative filtering
   ```Python
   import numpy as np
    from scipy.spatial import distance
    from multiprocessing import Pool

    top_k = 5
    Items = np.zeros((4500, 142))
    Users = np.zeros((142, 4500))
    for line in tqdm(train_data_5):
        user_id = int(line[0])
        item_id = int(line[1])
        val = line[-1]
        Items[item_id][user_id] = val
        Users[user_id][item_id] = val

    # Evaluating
    avg_mse = 0.0
    all_true = []
    all_pred = []
    for line in tqdm(dev_data):
        user_id = int(line[0])
        item_id = int(line[1])
        y_true = line[-1]
        item_vec = Items[item_id]
        user_vec = Users[user_id]
        # get similarity from Items
        all_dis = [distance.cosine(item_vec, x) for x in Items] 
        top_k_vec = np.argsort(np.array(all_dis))[1:6]
        all_true.append(y_true)
        ref_User = [Items[x][user_id] for x in top_k_vec]
        # get similarity from Users
        user_vec =Users[user_id]
        all_dis = [distance.cosine(user_vec, x) for x in Users]
        top_k_vec = np.argsort(np.array(all_dis))[1:6]
        ref_item = [Users[x][item_id] for x in top_k_vec]
        
        ref_vals = ref_User + ref_item
        y_pred = sum(ref_vals) / len(ref_vals)
        all_pred.append(y_pred)

    for t, p in zip(all_true, all_pred):
        avg_mse += MAE(y_true, y_pred)

    avg_mse = avg_mse /len(all_true)
   ```

## 实验结果

实验使用$MAE$来测试预测算法的性能， $MAE$的计算方法如下:

```python
def MAE(y_true, y_pred):
    res = y_true - y_pred
    if res < 0:
        res = - res
    if res > 0 and y_pred > 0:
        res = res*1.00000 / y_pred
        
    return res

```

由于算力的限制，只抽取5%的数据进行训练，另外抽取了10000条数据作为测试集，测试结果如下：

|Method|MAE_score|
|-----|-----|
|Umean| 1.32|
|Smean| 1.04|
|UCF| 0.33|
|SCF| 0.31|
|HCF| 0.28|

   

