
export class MeterList{


    constructor(server){
        this.url= "/meters/"
        this.method = "GET"
        this.collection = []
        this.path=new URL(server+this.url);
    }


    async update(){
        let response = await fetch(this.path,{
             credentials: 'include'
          } )


        if (response.ok) {
            this.collection=await response.json();
            return response.ok
        }
        else{ 
            console.log("network error:")
            console.log(response)
            return response.ok
        }
    }

    async append(name, description, data, verificationDate ){
        let newMeter = {Name:name,Description:description,LastData:data,VerificationDate:verificationDate, id:"-1" }

        let params=new URLSearchParams(newMeter);
        this.path.search=params

        let response = await fetch(this.path,{
            method:'PUT',
            credentials: 'include'
         } )


       if (response.ok) {
            newMeter.id=await response.text()
            this.collection.append(newMeter)
           return response.ok
       }
       else{ 
           console.log("network error:")
           console.log(response)
           return response.ok
       }

       
    }


}

export class Meter{

    constructor(server,id){
        this.id=id
        this.url= "/meters/"
        this.data = []
        this.path=new URL(server+this.url+id);
    }

    async get(){
        let response = await fetch(this.path,{
            credentials: 'include'
         } )

       if (response.ok) {
           this.data=await response.json();
           return response.ok
       }
       else{ 
           console.log("network error:")
           console.log(response)
           return response.ok
       }
    }

    async post(newData, newVerificationDate){
        let params=new URLSearchParams({
            Data : newData,
            NewVerification : newVerificationDate
          });
        this.path.search=params
        let response = await fetch(this.path,{
            method : 'POST',
            credentials: 'include',

         } )

       if (response.ok) {
           return response.ok
       }
       else{ 
           console.log("network error:")
           console.log(response)
           return response.ok
       }
    }

    async delete(pwd){
        let params=new URLSearchParams({
            UserPassword : pwd, //передача параметра как пароля!!!! TODO:Удали это зря что ли куки настраивал.
          });
        this.path.search=params
        let response = await fetch(this.path,{
            method : 'DELETE',
            credentials: 'include',

         } )

       if (response.ok) {
           return response.ok
       }
       else{ 
           console.log("network error:")
           console.log(response)
           return response.ok
       }
    }
}

