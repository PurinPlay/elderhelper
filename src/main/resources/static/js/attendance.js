function buildTable(data){
    var table = document.querySelector("table#show");
    data.forEach(rows => {
        var row = document.createElement("tr")
        rows.forEach(cell => {
            var td = document.createElement("td")
            td.innerText = cell
            row.appendChild(td)
        });
        table.appendChild(row)
    });
}
if(data!=-1){
    buildTable(data)
}
