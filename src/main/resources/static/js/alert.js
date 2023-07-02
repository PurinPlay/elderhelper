function checkStatus(){
    var url = new URL(window.location.href)
    if (url.searchParams.has("status")){
        var value = url.searchParams.get("status")
        switch (value){
            case "added":
                alert("Запись успешно добавлена")
                break
            case "edited":
                alert("Запись успешно изменена")
                break
            case "removed":
                alert("Запись успешно удалена")
                break
            case "failure":
                alert("В процессе работы произошла ошибка")
                break
            default:
                break
        }
    }
}