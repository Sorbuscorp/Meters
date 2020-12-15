from django.db import models
from django.contrib.auth.models import User
# Create your models here.



class Meter(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    name = models.CharField(max_length=30)
    description = models.CharField(max_length=100)
    verificationDate = models.DateField()

    def getLastData(self):
        return MeterData.objects.filter(meter=self).latest('timestamp')

class MeterData(models.Model):
    meter = models.ForeignKey(Meter, on_delete=models.CASCADE)
    value=models.FloatField()
    timestamp = models.DateTimeField(auto_now=True)
