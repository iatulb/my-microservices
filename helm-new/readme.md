##Wonderful dashboard


Follow instruction on for K8S dashboard
https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/

we need helm
its in SW. 


then create user with role binding


long live token

eyJhbGciOiJSUzI1NiIsImtpZCI6IjVJTzFrRE5QMTdkZjAzVnV1cllYRTlac1JyQ2V6Y1ctYmZfQ2VvQ1ZLQVkifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJhZG1pbi11c2VyIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZXJ2aWNlLWFjY291bnQubmFtZSI6ImFkbWluLXVzZXIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC51aWQiOiI3ZmNlNDFmMS1mODE3LTQ1NzAtYTJmZC1mOTRiNTNlZGQ5YTYiLCJzdWIiOiJzeXN0ZW06c2VydmljZWFjY291bnQ6a3ViZXJuZXRlcy1kYXNoYm9hcmQ6YWRtaW4tdXNlciJ9.JdoAU06znhNsu03FamcutEpHbMF-eBGOG2_C8o4_vIJ9IeCpPkx30JxTTJJ5H0FB8pbUxPsgYL2mHaZGXOZbvaetkV_8ldmFCwYsQB-tkIPYSMaMch8lMZY8VViu5nRoC8pN4mPeiXQMMXGKun_-EWmh3rwQfUl1ZruRLozyFUGSYPuz1eX9-joSoeOhi8HNuuLXvkNTfmF95lQFq7E0OFhCeRWMKFByRHNdpbTaFr9EmETaQcLAncO18DmRKbY-P63hDEK95OiQvDIcwKmyaQ0eOSM3DwRwBBDboeDyfTOPa8PsgaovFl1SxxGEKncx_xzd1_2LzNuNhSI7BRSJNQ



check port usages
netstat -ano | findstr :443

kill port in use
taskkill /PID 18344 /F

issue at helm
check PVC at kubernetes cluster/dashboard and delete them if available

kubectl -n default port-forward svc/kubernetes-dashboard-kong-proxy 8443:443

#Start kubernetes dashboard
helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard
#keycloak
#In helm folder
helm install keycloak keycloak
TIMEOUT /T 120
helm install kafka kafka
TIMEOUT /T 120
helm install prometheus kube-prometheus
TIMEOUT /T 120
helm install loki grafana-loki
TIMEOUT /T 120
helm install tempo grafana-tempo
TIMEOUT /T 120
helm install alloy grafana-alloy
helm install grafana grafana
TIMEOUT /T 120
helm install atulbank env/dev-env

try with new helm, bitnami is not working for tempo/loki tracing

#uninstall services
helm uninstall atulbank
helm uninstall grafana
helm uninstall alloy
helm uninstall tempo
kubectl delete -n default --now persistentvolumeclaim data-tempo-grafana-tempo-ingester-0 --cascade=background
helm uninstall loki
kubectl delete -n default --now persistentvolumeclaim data-loki-grafana-loki-querier-0 --cascade=background
kubectl delete -n default --now persistentvolumeclaim data-loki-grafana-loki-ingester-0 --cascade=background
helm uninstall prometheus
helm uninstall kafka
kubectl delete -n default --now persistentvolumeclaim data-kafka-controller-0 --cascade=background
helm uninstall keycloak
kubectl delete -n default --now persistentvolumeclaim data-keycloak-postgresql-0 --cascade=background
kubectl delete -n default --now persistentvolumeclaim export-0-loki-minio-0 --cascade=background
kubectl delete -n default --now persistentvolumeclaim export-1-loki-minio-0 --cascade=background
kubectl delete -n default --now persistentvolumeclaim data-loki-write-0 --cascade=background
kubectl delete -n default --now persistentvolumeclaim storage-tempo-0 --cascade=background


#Rollback to revision
helm rollback atulbank 1



[//]: # (kubectl scale statefulset tempo-grafana-tempo-ingester --replicas=0)

[//]: # (kubectl delete pvc data-tempo-grafana-tempo-ingester-0)

[//]: # (kubectl scale statefulset tempo-grafana-tempo-ingester --replicas=1)