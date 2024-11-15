# 1.使用curl创建分析流程
curl -X POST -H "Content-Type:application/json" -d '{"workflowName":"单菌分析流程","workflowCode":"singlestrain","step":"qcstatic","preStep":"[]"}' http://10.10.101.69:8080/workflow/create

# 2.使用curl创建分析算法
curl -X POST -H "Content-Type:application/json" -d '{"workflowCode":"singlestrain","step":"qcstatic","type":"docker","script":"docker.io/qcstat:v1.0.0"}' http://10.10.101.69:8080/script/create

# 3.使用curl创建分析任务参数模板
curl -X POST -H "Content-Type:application/json" -d '{"workflowCode":"singlestrain","step":"qcstatic","type":"Input","key":"read1","feature":"R1.fq.gz"}' http://10.10.101.69:8080/input/create

# 4.使用curl创建索引数据
curl -X POST -H "Content-Type:application/json" -d '{"batchNo":"20241112","sampleNo":"Test001","uniqueNo":"Test001","type":"rawdata","address":"[\"/rawdata/20241112/Test001.R1.fq.gz\"]"}' http://10.10.101.69:8080/index/create

# 5.使用curl创建分析任务
curl -X POST -H "Content-Type:application/json" -d '{"workflowCode":"singlestrain","step":"qcstatic","uniqueNo":"Test001"}' http://10.10.101.69:8080/task/create

curl -X POST -H "Content-Type:application/json" -d '{"type":"docker","key":"server","value":"10.10.108.12"}' http://10.10.101.69:8080/system/config
curl -X POST -H "Content-Type:application/json" -d '{"type":"docker","key":"outputDir","value":"/resultdata"}' http://10.10.101.69:8080/system/config





