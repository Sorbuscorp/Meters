from django.shortcuts import render
from django.http import HttpResponse, QueryDict
from django.contrib.auth import authenticate, login, logout
from Users import models
from django.http.multipartparser import MultiPartParser
from  django.contrib.auth.hashers import check_password


class UserController:
    def __init__(self, request):
        self.request=request
        self.method = request.method
    

    def login(self):
        params=self.request.GET
        logout(self.request)
        user = authenticate(self.request, username=params['Login'], password=params['Password'])
        if user is not None and user.is_active:
            login(self.request, user)
            return HttpResponse("Hello, {}!".format(params['Login']), status=200)# A backend authenticated the credentials
        else:
            return HttpResponse("Not autorized!", status=401)# No backend authenticated the credentials
        #return HttpResponse("WTF? why are you here???!")

    def register(self):
        params=self.request.GET
        #params=MultiPartParser(self.request.META, self.request, self.request.upload_handlers).parse()[0]
        if params.get('Login') is None:
            return HttpResponse(status=400)
        user=models.User.objects.create_user(params.get('Login'),params.get('Password'))
        user.set_password(params.get('Password'))
        user.first_name =params.get('Name')
        user.email=params.get('Email')
        user.profile.location=params.get("Address")
        user.save()
        return HttpResponse(status=200)

    def user_update(self):
        params=self.request.GET#params=MultiPartParser(request.META, request, request.upload_handlers).parse()[0]
        user=models.User.objects.get_by_natural_key(params.get('Login'))
        if user is not None:
            if check_password(params.get('OldPass'),user.password):
                user.set_password(params.get('NewPass'))
                user.first_name =params.get('Name')
                user.email=params.get('Email')
                user.profile.location=params.get("Address")
                user.save()
                return HttpResponse(status=200)
            else:
                return HttpResponse(status=401)
        return HttpResponse(status=400)

    def delete(self):
        params=self.request.GET#params=MultiPartParser(request.META, request, request.upload_handlers).parse()[0]
        user=models.User.objects.get_by_natural_key(params.get('Login'))
        if user is not None:
            if check_password(params.get('Password'),user.password):
                user.delete()
                return HttpResponse(status=200)
        return HttpResponse(status=400)

def logout_view(request):
    params=request.GET
    logout(request)
    return HttpResponse("Goodbye!!!", status=200)

def user(request):
    controller = UserController(request)
    try:
        if request.method == 'GET':
           return controller.login()
        elif request.method == 'POST':
           return controller.user_update()
        elif request.method == 'PUT':
           return controller.register()
        elif request.method == 'DELETE':
           return controller.delete()
    except:
        return HttpResponse(status=500)
