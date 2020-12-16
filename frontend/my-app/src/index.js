import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import {MetersList} from './components/MeterList'
import {UserPanel} from './components/UserComponent'
import "./index.css";
import { WiredTab, WiredTabs, WiredTextarea,WiredInput,WiredCard  } from "wired-elements"
import {User} from './Models/User'
import {server} from "./settigs"
// function userGetAction(){
//   let user = new User(server);
//   let a=user.login("ales", "789456");
//   console.log(a);
// }

// function userPutAction(){
//   let user = new User(server);
//   user.register("ales2", "123456","Mike","dewdedw@asdsad.com","Weuerfhuehf 46-37").
//     then(r=>console.log("reg"))
// }




class App extends React.Component {
  render() {
    return (
      <wired-card >
          <MetersList style={ {verticalAlign: 'top'}}/>
          <UserPanel />
      </wired-card >

     );
  }
}

ReactDOM.render(<App />, document.getElementById('root'));