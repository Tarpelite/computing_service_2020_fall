import requests
import json

from requests.api import head

api = "http://localhost:9999"
headers = {"Content-Type":"application/json"}

def show_text(res):
    print(json.loads(res.text)["res"])



if __name__ == "__main__":
    print("WARNING: YOU ARE SUBMITTING A LAUCHING JOB")
    ## submit a task
    task_base = input("Which base you choose to launch a nuclear ?")
    task_target = input("Where to destroy ?")
    task_dsp = input("Any other description ?")
    res = requests.post(api + "/submit_task",  data = json.dumps(
        {"task_dsp": task_dsp,
        "task_tgt": task_target,
        "task_base": task_base},    
    ), headers=headers)
    show_text(res)
    ## require general confirm
    key = input("Type into your General Crenditial and confirm:")
    res = requests.post(api + "/general_confirm", data=json.dumps(
        {"key":key}
    ), headers=headers)
    show_text(res)
    if "failed" in json.loads(res.text)["res"]:
        show_text(requests.post(api + "/task_abort"))
        exit()
    # require president confirm
    key = input("Type into your President Crenditial and confirm:")
    res = requests.post(api + "/president_confirm", data=json.dumps(
        {"key":key}
    ), headers=headers)
    show_text(res)
    if "failed" in json.loads(res.text)["res"]:
        show_text(requests.post(api + "/task_abort"))
        exit()
    # launch nuclear
    show_text(requests.post(api + "/nuclear_launch",
        data = json.dumps(
            {
                "task_base": task_base,
                "task_tgt": task_target
            }
        ),
        headers = headers
    ))
    exit()


    





    
