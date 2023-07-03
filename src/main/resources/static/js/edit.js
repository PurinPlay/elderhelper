var students = document.getElementById("student")
var schedule = document.getElementById("schedule")
var date = document.getElementById("date")
var button = document.getElementById("update")
date.value = "2023-07-05"
function change(){
    if(schedule.value==-1 || students.value == -1){
        button.disabled = true
    }else{
        button.disabled = false
    }
}
students.addEventListener("change", change)
schedule.addEventListener("change", change)
date.addEventListener("change", change)
checkStatus()