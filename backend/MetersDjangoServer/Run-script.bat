cmd Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
./DjangoEnv/Scripts/activate
python ./manage.py runserver 0.0.0.0:8082
