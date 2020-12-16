
import React from 'react';
import {server} from "../settigs"
import {User} from "../Models/User"
import { WiredTab, WiredTabs, WiredTextarea,WiredInput,WiredCard  } from "wired-elements"

class Register extends React.Component{
    constructor(props) {
        super(props)        
        this.handleInputChange = this.handleInputChange.bind(this);
        this.Register = this.Register.bind(this);
        this.state = {
            login : "",
            pass : "",
            name :"",
            email : "",
            address : ""
        };
    }
    handleInputChange(event){ 
        let name = event.target.name
        const value = event.target.value
        this.setState({[name]:value})
    }
    async Register(){
        let user = new User(server);
        let a=user.register(this.state.login, this.state.pass,this.state.name,this.state.email,this.state.address);
    }
    render(){
        return(
            <form >
                <wired-input  name={"login"} placeholder="Логин"style={{display:"block" ,width:"98%"}}onInput={this.handleInputChange }/>
		        <wired-input  name={"pass"} type="password"style={{display:"block" ,width:"98%"}} placeholder="Пароль"onInput={this.handleInputChange }/>
                <wired-input  name={"name"} placeholder="Имя" style={{display:"block" ,width:"98%"}}onInput={this.handleInputChange }/>
                <wired-input  name={"email"} placeholder="Е-mail"style={{display:"block",width:"98%"}}onInput={this.handleInputChange }/>
                <wired-input  name={"address"} placeholder="Адрес"style={{display:"block",width:"98%"}}onInput={this.handleInputChange }/>

                <wired-button  type="submit" style={{display:"block", color : "blue"}} onClick={this.Register}>"Регистрация"</wired-button>
            </form>
        )

    }
}

class Login extends React.Component{
    constructor(props) {
        super(props)        
        this.handleInputChange = this.handleInputChange.bind(this);
        this.login = this.login.bind(this);
        this.state = {
            login : "",
            pass : ""
        };
    }
    handleInputChange(event){ 
        let name = event.target.name
        const value = event.target.value
        this.setState({[name]:value})
    }
    async login(){
        let user = new User(server);
        let a=user.login(this.state.login, this.state.pass);
    }

    render(){
        return(
            <form >
                <wired-input name={"login"} placeholder="Логин" style={{display:"block",width:"98%"}} onInput={this.handleInputChange }/>
		        <wired-input name={"pass"} type="password"  style={{display:"block",width:"98%"} } placeholder="Пароль" onInput={this.handleInputChange }/>
                <wired-button type="submit" style={{display:"block", color : "blue"}} onClick={this.login}>"Вход"</wired-button>
            </form>
        )

    }
}

export class UserPanel extends React.Component{
    constructor(props) {
        super(props)        
        this.state = {

        };
    }

    render(){
        return(
        <wired-card style={ {verticalAlign: 'top'}}>
            <wired-tabs selected={"login"}>
                <wired-tab id={"login"} name={"login" } label={"Я уже смешарик"} style={{display : 'block'}}>   
                <Login/>
                </wired-tab>    
                <wired-tab id={"Reg"} name={"Reg" } label={"Я новенький "} style={{display : 'block'}}>    
                <Register/>
                </wired-tab> 
            </wired-tabs>
        </wired-card>
        )

    }
}