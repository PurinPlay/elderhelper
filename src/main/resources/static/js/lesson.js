var submit = document.getElementById("send")
var select = document.getElementById("selected_lesson")
var remove = document.getElementById("remove")
select.addEventListener("change", ()=>{
    if(select.value == -1){
        submit.value = "Добавить"
        remove.disabled = true
    }else{
        submit.value = "Обновить"
        remove.disabled = false
    }
})
checkStatus()