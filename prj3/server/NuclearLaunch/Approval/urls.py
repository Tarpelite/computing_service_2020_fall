from django.urls import path
from . import views

app_name = "Approval"
urlpatterns = [
    path("submit_task", views.submit_task),
    path ("general_confirm", views.general_confirm),
    path("president_confirm", views.president_confirm),
    path("task_abort", views.task_abort),
    path("nuclear_launch", views.nuclear_launch)
]