

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
        let response = await fetch(this.path ,{
             credentials: 'include' 
            // headers:{"Access-Control-Allow-Credentials":""}
          });
        if (response.ok) {
            console.log("user autorized!! ")
            return response
        }
        else{ 
            console.log("network error:")
            console.log(response)
            return response
        }
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
        let response = await fetch(this.path ,{
            method:this.method
            // credentials: 'include'  ,
            // headers:{"Access-Control-Allow-Credentials":""}
          });
        if (response.ok) {
            console.log("user registered!! ")
            return response
        }
        else{ 
            console.log("network error:")
            console.log(response)
            return response
        }
    }
}