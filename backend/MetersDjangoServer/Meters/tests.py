"""
This file demonstrates writing tests using the unittest module. These will pass
when you run "manage.py test".

Replace this with more appropriate tests for your application.
"""

import django
from django.test import TestCase
from django.test import Client

# TODO: Configure your database in settings.py and sync before running tests.


class user_test(TestCase):
    #fixtures = ["fixture1.json"]
    def setUp(self):
        self.client=Client()
        self.client.put('/user/?Login=ales1&Password=123456&Name=Mike&Email=dewdedw@asdsad.com&Address=Weuerfhuehf 46-37')


    def tearDown(self):
        pass

    def test_user_register(self):
        response = self.client.put('/user/?Login=ales2&Password=123456&Name=Mike&Email=dewdedw@asdsad.com&Address=Weuerfhuehf 46-37')
        self.assertEqual(response.status_code, 200)

    def test_user_alredy_register(self):
        response = self.client.put('/user/?Login=ales1&Password=123456&Name=Mike&Email=dewdedw@asdsad.com&Address=Weuerfhuehf 46-37')
        self.assertEqual(response.status_code, 500)

    def test_user_login(self):
        response = self.client.get('/user/',{'Login': 'ales1', 'Password':'123456' })
        self.assertEqual( response.status_code, 200)

    def test_user_delete(self):
        response = self.client.delete('/user/?Login=ales1&Password=123456')
        self.assertEqual( response.status_code, 200)
