async function  safeFetch(path, method){
    let response;
    try {
        response = await fetch(path,{
            method : method,
            credentials: 'include',

         } )
    } catch (error) {
        response={}
        response.ok=false
    }
    

   if (response.ok) {
       return response
   }
   else{ 
       console.log("network error:")
       console.log(response)
       return response
   }
    
}

export class User{

    constructor(server){
        this.url= "/user/"
        this.method = "GET"        
        this.path=new URL(server+this.url);
    }

    async login(login, password){
        let params=new URLSearchParams({
              Login : login,
              Password : password
            });
        this.path.search=params;
        let response = await safeFetch(this.path, "GET")
        if (response.ok)
            console.log("user autorized!! ")
        
        return response.ok
    }
    async register(login, password, name, email, address){
        this.method = "PUT"
        let params=new URLSearchParams({
              Login : login,
              Password : password,
              Name : name,
              Email : email,
              Address : address
            });
        this.path.search=params;
        let response = await safeFetch(this.path, "PUT")
        if (response.ok) 
            console.log("user registered!! ")

    }
}