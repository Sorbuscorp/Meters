import React from 'react';
import {MeterList} from "../Models/Meters"
import {server} from "../settigs"
import { WiredTab, WiredTabs, WiredTextarea,WiredInput,WiredCard  } from "wired-elements"

class TabItem extends React.Component{
    constructor(props) {
        super(props)
        this.state = {
            hasId: props.value.ID !== undefined,
            ID:  props.value.ID !== undefined? this.props.value.ID : "+",
            btnAction : null,
            nameBlocked : props.value.ID !== undefined ? true : false,

            description : props.value?.Description,
            name : props.value?.Name,
            date : props.value?.VerificationDate,
            data : props.value?.LastData,

        };
        this.handleInputChange = this.handleInputChange.bind(this);
    }
    handleInputChange(event){ 
        const name = event.target.name
        const value = event.target.value
        this.setState({[name]:value})
    }

    render() {
        return (        
            <wired-tab name={this.state.ID } label={this.state.name}>
                <form>
                <wired-card style={ {verticalAlign: 'top'} }>
                    <wired-toggle name="nameBlocked" checked = {!this.state.nameBlocked? 'checked' : null} onClick={this.handleInputChange }></wired-toggle>
                    <wired-input name="name" placeholder="Введите название" value ={this.state.name} disabled={this.state.nameBlocked? 'disabled' : null}  onChange={this.handleInputChange }/>
                </wired-card>
                <wired-textarea style={ {verticalAlign: 'top'}} placeholder="Добавьте описание" rows="9" value ={this.state.description}/>
                <wired-card style={ {verticalAlign: 'top'}}>
                    <p>Дата поверки:</p>
                    <wired-input name="date" type="date" value ={this.state.date} />

                    <p>Последние показания:</p>
                    <wired-input name="data" type="number" step="0.1" value ={this.state.data} />
                </wired-card>
                <wired-button style={ {verticalAlign: 'bottom'} } onClick={this.state.btnAction}>{this.state.hasId? "Обновить данные" : "Добавить счетчик"}</wired-button>
                {/* {JSON.stringify(props.value)} */}
                </form>
            </wired-tab>
            );
    }
}

export class MetersList extends React.Component{
    constructor(props) {
      super(props)
      this.state = {
          data: {},
          list: []

      };
      this.m=new MeterList(server);
  
      this.update();
    }

    
  
    async update() {
        let result = await this.m.update();
        
        this.setState({data:this.m.collection});
        console.log(this.state.data)
        
        const TabItems = this.state.data.map((data) =>
                <TabItem key={data.ID} value={data} />);
        
        this.setState({list:TabItems});
    }
  
    render() {
      return (
          <div className="MetersTabs">
          {/* <h1>We have next meters, { JSON.stringify(this.state.list)}</h1> */}
          <wired-tabs selected={this.state.list[0]?.key}>
            {this.state.list}
            <TabItem key={-1} value={[]}/>
          </wired-tabs>
          </div>
        );
    }
  }