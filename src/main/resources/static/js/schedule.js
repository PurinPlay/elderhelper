var dayOfTheWeek = document.getElementById("DOTW")
var lesson = document.getElementById("lesson")
var type = document.getElementById("type")
var execute = document.getElementById("execute")
var _delete = document.getElementById("delete")
var action = document.getElementsByName("action")
function change(){
    console.log(action[0].checked || dayOfTheWeek.value, lesson.value, type.value)
    if(dayOfTheWeek.value==-1 || lesson.value==-1 || type.value==-1){
        execute.disabled = true
        _delete.disabled = true
    }else{
        execute.disabled = false
        _delete.disabled = false
    }
}
function actionchanged(){
    if(action[0].checked || dayOfTheWeek.value==-1 || lesson.value==-1 || type.value==-1){
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
action.forEach(element => {
    element.addEventListener("click", actionchanged)
});
checkStatus()