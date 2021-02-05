# Цели и Шаги
## FrontEnd: (не реализовано)

## BaskEnd: (не реализовано)
Server:
1. Подключение к BD реализовано на HiberNAT. Конфигурационный файл Server/resources/configuration/hibernate.cfg.xml
2. Таблицы с сущностями мапим в сущности Java
 
## Прочее: (не реализовано)

### Рабочие заметки 
1. class="col-xl-4 col-lg-3 col-md-3 col-sm-5 col-xs iButton
2.     <!--https://stackoverflow.com/questions/40773248/how-to-change-pages-based-on-url
       https://stackoverflow.com/questions/10816073/how-to-do-paging-in-angularjs&ndash;&gt;
       &lt;<pagination
       ng-model="currentPage"
       total-items="todos.length"
       max-size="maxSize"
       boundary-links="true">
       </pagination>-->
3. heroku ps:scale web=1 - включить ПО на хероку
4. <div ng-include="'invoice/invoiceEdit.html'"></div>
5.  sessionStorage.setItem("userID", response.data.userId);
    sessionStorage.setItem("userInfo", JSON.stringify(response.data));
    return JSON.parse(sessionStorage.getItem("userInfo"));
6.     {
           "id": 1554,
           "datacreate": "2020-11-20T11:03:45.065+00:00",
           "department": "1",
           "comment": "test",
           "ordernumber": "WL-REQ-111111111",
           "invoicenumber": null,
           "senttoapprove": null,
           "senttopurchase": null,
           "senttoprice": null,
           "totalprice": null,
           "resolveddate": null,
           "customer": {
               "id": 1
           },
           "purchases": [
               {
                   "id": 3827,
                   "nomenclature": {
                       "id": 444,
                       "comment": "test",
                       "price": 100,
                       "submitDate": "2020-06-01T00:00:00.000+00:00",
                       "nomenclature": "HP 24ea 23.8",
                       "manufacturer": "HP",
                       "code": "00000275978",
                       "expiredDate": null
                   },
                   "count": 2,
                   "approver": null,
                   "resolvingdate": null,
                   "comment": "test",
                   "buyingPrice": 100,
                   "commentnumenclature": null
               }
           ],
           "histories": [
               {
                   "id": 2,
                   "submitdate": "2020-11-24T00:00:00.000+00:00",
                   "customer": {
                       "id": 17
                   },
                   "step": {
                       "id": 2
                   },
                   "stepcomment": [
                       {
                           "id": 11,
                           "customer": {
                               "id": 17
                           },
                           "comment": "test",
                           "attachedfileid": null,
                           "submitdate": "2020-11-24T10:00:00.000+00:00"
                       }
                   ]
               }
           ]
       }