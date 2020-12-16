from django.http import HttpResponse
from django.http.multipartparser import MultiPartParser
from django.core.exceptions import ObjectDoesNotExist
import dateutil.parser
from Meters.models import *
import json
from django.contrib.auth.decorators import login_required
from  django.contrib.auth.hashers import check_password


def convert_to_float(str_data):
	if str_data == '':
		return float(0)
	try:
		ret=float(str_data)
	except Exception:
		return float(0)
	else:
		return ret


@login_required
def meter(request):
	if request.method == 'GET':
		try:
			params=request.GET
			user=request.user
			if not user.is_authenticated:
				return HttpResponse(status=401)
			meters = Meter.objects.filter(user=user)
			collection = []
			for m in meters:
				record={
					'ID': m.id, 
					'Name': m.name,
					'Description': m.description,
					'LastData': m.getLastData().value,
					'VerificationDate': m.verificationDate.isoformat(),
					}
				collection.append(record)
			return HttpResponse(json.dumps(collection),status=200)
		except:
				return HttpResponse(status=400)

	elif  request.method == 'PUT':
		try:
			params=request.GET#params=MultiPartParser(request.META, request, request.upload_handlers).parse()[0]
			name=params.get('Name')
			if (name is None) or (name==''):
				name='some meter'
			description=params.get('Description')
			if description is None or (description==''):
				description='this is meter'
			data=convert_to_float(params.get('LastData'))

			verdate = params.get('VerificationDate')
			if verdate is None or (verdate==''):
				verDate = datetime.datetime.today()
			else:
				verDate=dateutil.parser.parse(verdate).date()
			user=request.user
			if not user.is_authenticated:
				return HttpResponse(status=401)
			meter = Meter(user=user, name=name, description=description, verificationDate=verDate)
			meter.save()
			data = MeterData(meter=meter, value=data)
			data.save()
			return HttpResponse(status=200)
		except:
				return HttpResponse(status=400)

	return HttpResponse(status=405)

@login_required
def meter_id(request, id):
	if request.method == 'GET':
		try:
			user=request.user
			if not user.is_authenticated:
				return HttpResponse(status=401)

			meter = Meter.objects.get(id=id)
			if meter.user!=user:
				return HttpResponse(status=401)

			meterDataList=MeterData.objects.filter(meter=meter)

			collection = {}
			collection['Name']=meter.name
			collection['Description']=meter.description
			collection['VerificationDate']=meter.verificationDate.isoformat()
			collection['LastData'] = meter.getLastData().value
			collection['MeterDataList'] = [{'data':var.value, 'dateTime':var.timestamp.isoformat("T","seconds")[0:-6]} for var in meterDataList]
			return HttpResponse(json.dumps(collection),status=200)
		except:
			return HttpResponse(status=400)

	elif request.method == 'POST':
		try:
			params=request.GET	#params=MultiPartParser(request.META, request, request.upload_handlers).parse()[0]
		
			user=request.user
			if not user.is_authenticated:
				return HttpResponse(status=401)

			meter = Meter.objects.get(id=id)
			if meter.user!=user:
				return HttpResponse(status=401)

		
			new_value=convert_to_float(params['Data'])
			new_verification = params['NewVerification']

			if (new_verification is not None) and (new_verification!=''):
				meter.verificationDate = dateutil.parser.parse(new_verification).date()
			data=MeterData(meter=meter, value=new_value)
			data.save()
			meter.save()

			return HttpResponse(status=200)
		except:
			return HttpResponse(status=400)

	elif request.method == 'DELETE':
		try:
			params=request.GET	#params=MultiPartParser(request.META, request, request.upload_handlers).parse()[0]

			user=request.user
			if not user.is_authenticated:
				return HttpResponse(status=401)

			meter = Meter.objects.get(id=id)

			if meter is None:
				return HttpResponse(status=404)

			if meter.user!=user:
				return HttpResponse(status=401)

			#не особо нужная проверка но для сохранения соответствия API пусть будет
			if not check_password(params.get('UserPassword'),user.password):
				return HttpResponse(status=401)

			meter.delete()

			return HttpResponse(status=200)
		except ObjectDoesNotExist:
			return HttpResponse(status=404)
		except:
			return HttpResponse(status=400)

	return HttpResponse(status=405)
