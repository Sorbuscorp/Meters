"""
Django settings for MetersDjangoServer project.

Based on by 'django-admin startproject' using Django 2.1.2.

For more information on this file, see
https://docs.djangoproject.com/en/2.1/topics/settings/

For the full list of settings and their values, see
https://docs.djangoproject.com/en/2.1/ref/settings/
"""

import os
import posixpath
from corsheaders.defaults import default_headers, default_methods
# Build paths inside the project like this: os.path.join(BASE_DIR, ...)
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Quick-start development settings - unsuitable for production
# See https://docs.djangoproject.com/en/2.1/howto/deployment/checklist/

# SECURITY WARNING: keep the secret key used in production secret!
SECRET_KEY = '8c546e2d-369f-4d75-a6b9-a183a1d7e657'

# SECURITY WARNING: don't run with debug turned on in production!
DEBUG = True

APPEND_SLASH=False


# Application references
# https://docs.djangoproject.com/en/2.1/ref/settings/#std:setting-INSTALLED_APPS
INSTALLED_APPS = [
    # Add your apps here to enable them
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'corsheaders',

    #LOCAL
    'Users',
    'Meters'
    ]

# Middleware framework
# https://docs.djangoproject.com/en/2.1/topics/http/middleware/
MIDDLEWARE = [
    'MetersDjangoServer.middleware.CorsMiddleware',
    'corsheaders.middleware.CorsMiddleware',
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    #'django.middleware.csrf.CsrfViewMiddleware',
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
    
]
#Authentication backends
AUTHENTICATION_BACKENDS = (
        'django.contrib.auth.backends.ModelBackend',
    )

ROOT_URLCONF = 'MetersDjangoServer.urls'

# Template configuration
# https://docs.djangoproject.com/en/2.1/topics/templates/
TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
        'DIRS': [],
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]

WSGI_APPLICATION = 'MetersDjangoServer.wsgi.application'

# Database
# https://docs.djangoproject.com/en/2.1/ref/settings/#databases
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),
    }
}

# Password validation
# https://docs.djangoproject.com/en/2.1/ref/settings/#auth-password-validators
AUTH_PASSWORD_VALIDATORS = [
    #{
    #    'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    #},
    #{
    #    'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    #},
    #{
    #    'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    #},
    #{
    #    'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    #},
]


# Internationalization
# https://docs.djangoproject.com/en/2.1/topics/i18n/
LANGUAGE_CODE = 'en-us'
TIME_ZONE = 'UTC'
USE_I18N = True
USE_L10N = True
USE_TZ = True

CORS_ORIGIN_ALLOW_ALL = False
ALLOWED_HOSTS = []
CORS_ALLOWED_ORIGINS = [
    "http://localhost:3000"
]


CORS_ALLOW_METHODS=list(default_methods)
CORS_ALLOW_HEADERS= list(default_headers) + [
        #'Access-Control-Allow-Headers',
        #'Access-Control-Allow-Credentials',
    ]

#CORS_EXPOSE_HEADERS=[
#    'Access-Control-Allow-Headers',
#    'Access-Control-Allow-Credentials',
#    ]
CORS_ALLOW_CREDENTIALS=True
<<<<<<< Updated upstream
CSRF_TRUSTED_ORIGINS =[
    "http://localhost:3000"
]
CORS_ORIGIN_WHITELIST = [
    'http://localhost:3000',
 ]
=======
# CSRF_TRUSTED_ORIGINS =[
    # "http://localhost:3000",

# ]
# CORS_ORIGIN_WHITELIST = [
    # 'http://localhost:3000',
 # ]
>>>>>>> Stashed changes

#comment for standart cookies
if DEBUG:
    SESSION_COOKIE_SAMESITE=None
    SESSION_COOKIE_SECURE = True
# Static files (CSS, JavaScript, Images)
# https://docs.djangoproject.com/en/2.1/howto/static-files/
STATIC_URL = '/static/'
STATIC_ROOT = posixpath.join(*(BASE_DIR.split(os.path.sep) + ['static']))
