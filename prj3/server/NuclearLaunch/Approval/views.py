from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.http import HttpResponse
import os
# Create your views here.

GENERAL_KEY = "123"
PRESIDENT_KEY="456"
TASK_NO = 0
TASK_DSP = ""
TASK_TGT = ""
TASK_BASE = "abc"

@api_view(["POST"])
def submit_task(request):
    if request.method == "POST":
        data = request.data
        try:
            TASK_NO = data["task_no"].strip()
            TASK_DSP = data["task_dsp"].strip()
            TASK_TGT = data["task_tgt"].strip()
            TASK_BASE = data["task_base"].strip()
        except Exception as e:
            return Response(
                {"res":"SUBMIT FAILED!"},
                status=status.HTTP_200_OK
            )
        return Response({"res":"General Confirmed !"}, status=status.HTTP_200_OK)
        

@api_view(["POST"])
def general_confirm(request):
    if request.method == "POST":
        data = request.data
        if data["key"].strip() != GENERAL_KEY:
            return Response({"res":"General confirme failed! Cancel Lanuch !"}, status=status.HTTP_200_OK)
        return Response({"res":"General Confirmed !"}, status=status.HTTP_200_OK)

@api_view(["POST"])
def president_confirm(request):
    if request.method == "POST":
        data = request.data
        if data["key"].strip() != PRESIDENT_KEY:
            return Response({"res":"President confirme failed! Cancel Lanuch !"}, status=status.HTTP_200_OK)
        return Response({"res":"President Confirmed !"}, status=status.HTTP_200_OK)

@api_view(["POST"])
def task_abort(request):
    if request.method == "POST":
        
        return Response(
            {"res": "TASK ABORTED!"},
            status=status.HTTP_200_OK
        )

@api_view(["POST"])
def nuclear_launch(request):
    if request.method == "POST":
        data = request.data
        res = {
            "res":
            "Missile lanuched from {} to {}".format(
                data["task_base"], data["task_tgt"]
            )
        }
        
        return Response(
            res,
            status=status.HTTP_200_OK
        )

