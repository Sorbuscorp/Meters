import React from 'react';
import {MeterList,Meter} from "../Models/Meters"
import {server} from "../settigs"
import { WiredTab, WiredTabs, WiredTextarea,WiredInput,WiredCard  } from "wired-elements"

class DataList extends React.Component{
    constructor(props) {
        super(props)

        
        this.get = this.get.bind(this);

        this.state = {
            meter : new Meter(server, props.ID),
            list : []
        };
        this.get()
    }

    async get(){
        let result=await this.state.meter.get()
        if(result){
            console.log(this.state.meter.data.MeterDataList)
            this.setState({meter:this.state.meter})
        }
        let i=0;
        let p= this.state.meter.data.MeterDataList.map((data) =>
        <p key={this.state.meter.id+":"+(i++)}>{'value:\t'+new String(data.data)+"\t date: "+new String(data.dateTime)}</p>);

        this.setState({list:p})

        
    }
    render(){
        return (
            <div>
            <wired-card style={{width:"98%"}}>
                {this.state.list}
            </wired-card>
            </div>
        )
    }
}



class TabItem extends React.Component{
    constructor(props) {
        super(props)

        this.UpdateAction = this.UpdateAction.bind(this);
        this.DeleteAction = this.DeleteAction.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        
        this.state = {
            ID:  props.value.ID,
            btnUpdAction : this.UpdateAction ,
            parentAction : this.props.Action,
            // nameBlocked : props.value.ID !== undefined ? true : false,
            
            description : props.value?.Description,
            name : props.value?.Name,
            date : props.value?.VerificationDate,
            data : props.value?.LastData,

        };
        
        
    }
    handleInputChange(event){ 
        let name = event.target.name
        const value = event.target.value
        let a= this.state
        this.setState({[name]:value})
    }
    async UpdateAction(event){
        let meter=new Meter(server, this.state.ID)
        let result=await meter.post(this.state.data, this.state.date)
        if (result)
            alert("Ваши показания обновлены");
        else
            alert("что-то пошло не так")
    }
    
    async DeleteAction(){
        console.log('Delete')
        let meter=new Meter(server, this.state.ID)
        const pwd = "789456";//prompt("Для подтверждения удаления введите ваш пароль:", "789456")
        if(await meter.delete(pwd))
            console.log("server delete")

        this.state.parentAction()
    }

    render() {
        return (        
            <wired-tab id={"tab"} name={this.state.ID } label={this.state.name}>
                <wired-input name="name" disabled={"disabled"} placeholder="Введите название" value ={this.state.name} /> {/*onInput={this.handleInputChange }*/}
                <wired-textarea style={ {verticalAlign: 'top'}}  disabled={"disabled"} placeholder="Добавьте описание" rows="9" value ={this.state.description}/>
                <wired-card style={ {verticalAlign: 'top'}}>
                    <p>Дата поверки:</p>
                    <wired-input name="date" type="date" value ={this.state.date} onInput={this.handleInputChange }/>

                    <p>Последние показания:</p>
                    <wired-input name="data" type="number" step="0.1" value ={this.state.data} onInput={this.handleInputChange } />
                </wired-card>

                <wired-card style={ {verticalAlign: 'top'}}>
                    <wired-button style={ {color : "blue"} } onClick={this.state.btnUpdAction}>{"Отправить показания"}</wired-button>
                    <wired-button style={ {color : "red"}  } onClick={this.DeleteAction}>{"Удалить счетчик"}</wired-button>
                </wired-card>
                
                <DataList style={{float:"left",display : 'block'}} ID={this.state.ID }/>                
 
            </wired-tab>
            );
    }
}


class NewTabItem extends React.Component{
    constructor(props) {
        super(props)
        this.handleInputChange = this.handleInputChange.bind(this);
        this.CreateAction = this.CreateAction.bind(this);

        this.state = {
            ID :"+",
            description : "",
            name : "",
            date : new Date().toISOString().slice(0, 10),
            data : "",
            parentAction : this.props.Action

        }
    }
    handleInputChange(event){ 
        let name = event.target.name
        if(name===undefined)
        {
            name = "description"
        }
        const value = event.target.value
        this.setState({[name]:value})
    }

    CreateAction(){
        console.log('Create')
        let data = {description:this.state.description,name: this.state.name,date: this.state.date, data: this.state.data}
        this.state.parentAction(data)

    }

    render() {
        return (        
            <wired-tab id={"tab"} name={"+"} label={"+"}>
                <wired-input name="name" placeholder="Введите название" value ={this.state.name} onInput={this.handleInputChange }/>
                <wired-textarea style={ {verticalAlign: 'top'}}  placeholder="Добавьте описание" rows="9" value ={this.state.description} onInput={this.handleInputChange }/>
                <wired-card style={ {verticalAlign: 'top'}}>
                    <p>Дата поверки:</p>
                    <wired-input name="date" type="date" value ={this.state.date} onInput={this.handleInputChange }/>

                    <p>Последние показания:</p>
                    <wired-input name="data" type="number" step="0.1" value ={this.state.data} onInput={this.handleInputChange }/>
                </wired-card>
                <wired-button style={ {verticalAlign: 'bottom'} } onClick={this.CreateAction}>{"создать счетчик"}</wired-button>
            </wired-tab>
            );
    }
}

export class MetersList extends React.Component{
    constructor(props) {
      super(props)

      this.CreateAction = this.CreateAction.bind(this);
      this.DeleteAction = this.DeleteAction.bind(this);
      this.update = this.update.bind(this);

      this.state = {
          //collection: {},
          list: [],
          meterList :new MeterList(server)

      };
        
      this.update();    
      
    }

    CreateAction(data){
        this.state.meterList.append(data.name, data.description, data.data, data.date)
        this.setState({meterList:this.state.meterList})
        console.log('Parent Create')
        this.update()
    }

    DeleteAction(){
        console.log('Parent Delete')
        this.update()
    }
  
    async update() {
        let result = await this.state.meterList.load();
        
        let TabItems = this.state.meterList.collection.map((data) =>
                <TabItem key={data.ID} value={data} Action={this.DeleteAction}/>);
        
        TabItems.push(<NewTabItem key={-1} value={[]} Action={this.CreateAction}/>)
        this.setState({list:TabItems});
        console.log(this.state.list)
    }
  
    render() {
      return (
          <wired-card className="MetersTabs">
          {/* <h1>We have next meters, { JSON.stringify(this.state.list)}</h1> */}
          <wired-tabs selected={this.state.list[0]?.key}>
            {this.state.list}          
          </wired-tabs>
          </wired-card>
        );
    }
  }