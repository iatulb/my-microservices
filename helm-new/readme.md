##Wonderful dashboard


Follow instruction on for K8S dashboard
https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard/

we need helm
its in SW. 

kubectl apply -f .\dashboard-adminuser.yml
kubectl apply -f .\dashboard-rolebinding.yml
kubectl apply -f .\secret.yml   
kubectl create token admin-user


then create user with role binding


long live token

eyJhbGciOiJSUzI1NiIsImtpZCI6IldzSlJMdllZZFNJelBjdmw5MlZOWkpxOExtU0hHOVZjNGxmeVRDQU9ZSU0ifQ.eyJhdWQiOlsiaHR0cHM6Ly9rdWJlcm5ldGVzLmRlZmF1bHQuc3ZjLmNsdXN0ZXIubG9jYWwiXSwiZXhwIjoxNzczMTI4MDgyLCJpYXQiOjE3NzMxMjQ0ODIsImlzcyI6Imh0dHBzOi8va3ViZXJuZXRlcy5kZWZhdWx0LnN2Yy5jbHVzdGVyLmxvY2FsIiwianRpIjoiODRhMTY2ZjMtMmIyMC00YTQ2LWI2ODQtOTMxOTdhNzBmZTZlIiwia3ViZXJuZXRlcy5pbyI6eyJuYW1lc3BhY2UiOiJkZWZhdWx0Iiwic2VydmljZWFjY291bnQiOnsibmFtZSI6ImFkbWluLXVzZXIiLCJ1aWQiOiI5MmJmNjFhZC1mNDMwLTRkOTYtYThlMy0wZTY3ZjYzNDNkNTUifX0sIm5iZiI6MTc3MzEyNDQ4Miwic3ViIjoic3lzdGVtOnNlcnZpY2VhY2NvdW50OmRlZmF1bHQ6YWRtaW4tdXNlciJ9.BzXEc7yEZLSsIJ1xwxW5tz0CxDkk0q5zvjQI_TCi0HxR8tRomdHJVNftY9HgKCkssNMSgm888ZEyc5hNleerHTCZG8IsMKFn5TqinjfHspKOYy7NaeB-7YdO_qKhn2vQhbZtc4bZx9Q8JCCH-0Z_DdOFK6x4wt-xx42iQcRWgBt45tzHMgyWTJqHDJG68fL_LT2L0bLFvHN5Vfp2F6hAWyp5ZLw87FCgmZiyzg_6PEIGubCaFl0fcM_bKYNQVPrTkQfWf8K1UYMzKYnZ7aJf7wvNTvESQ3HePvPxDKd5UxT4BVvNFB4b5VRSCxdX7qFR-zSBfx4TFmYEzGreWMRfEQ



check port usages
netstat -ano | findstr :443

kill port in use
taskkill /PID 18344 /F

issue at helm
check PVC at kubernetes cluster/dashboard and delete them if available

kubectl -n default port-forward svc/kubernetes-dashboard-kong-proxy 8443:443


# Remove the old, broken repository
helm repo remove kubernetes-dashboard

# Add the new archived repository URL
helm repo add kubernetes-dashboard https://kubernetes-retired.github.io/dashboard/

# Update your local cache
helm repo update


# Start kubernetes dashboard
helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard


# Start headlamp dashboard
helm repo add headlamp https://kubernetes-sigs.github.io/headlamp/
helm install my-headlamp headlamp/headlamp 
kubectl  port-forward svc/my-headlamp 8074:80

# token generator
kubectl create token my-headlamp 


# create docker image
# in account project
mvn compile jib:dockerBuild

# keycloak
# In helm-new folder
helm install keycloak keycloak

# available on http://localhost/
password is in k8s secret [admin]


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
# available on http://localhost:3000/
TIMEOUT /T 120
helm install atulbank env/dev-env

try with new helm, bitnami is not working for tempo/loki tracing

# uninstall services
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



gcloud container clusters get-credentials cost-optimized-cluster-1 --location=us-central1-c


