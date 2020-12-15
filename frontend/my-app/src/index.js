import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import {MetersList} from './components/MeterList'

import "./index.css";

import {User} from './Models/User'
import {server} from "./settigs"
function userGetAction(){
  let user = new User(server);
  let a=user.login("ales", "789456");
  console.log(a);
}

// function userPutAction(){
//   let user = new User(server);
//   user.register("ales2", "123456","Mike","dewdedw@asdsad.com","Weuerfhuehf 46-37").
//     then(r=>console.log("reg"))
// }




class App extends React.Component {
  render() {
    return (
      <div className="Container">
        <button onClick={userGetAction} > Логин!!! </button>
        <MetersList/>
      </div>

     );
  }
}

ReactDOM.render(<App />, document.getElementById('root'));