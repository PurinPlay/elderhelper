var dayOfTheWeek = document.getElementById("DOTW")
var scheduleList = document.getElementById("scheduleList")
var lesson = document.getElementById("lesson")
var type = document.getElementById("type")
var execute = document.getElementById("execute")
var _delete = document.getElementById("delete")
var action = document.getElementsByName("action")
function change(){
    if(dayOfTheWeek.value==-1 || lesson.value==-1 || type.value==-1){
        execute.disabled = true
    }else{
        execute.disabled = false
    }
    if(scheduleList.value==-1){
        _delete.disabled = true
        execute.value = "Добавить"
    }else{
        _delete.disabled = false
        execute.value = "Обновить"
    }
}
dayOfTheWeek.addEventListener("change", change)
lesson.addEventListener("change", change)
type.addEventListener("change", change)
scheduleList.addEventListener("change", change)
checkStatus()