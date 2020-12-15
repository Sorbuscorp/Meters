from django.urls import path, re_path

from Meters.views import *

urlpatterns = [
    path('', meter),
    re_path(r'^(\d+)', meter_id)
]
