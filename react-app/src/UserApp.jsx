
import React, {useEffect} from  'react';

import './UserApp.css'

import {AddConfiguration} from './utils/rest-calls'

import ConfigurationForm from "./ConfigurationForm.jsx";

export default function UserApp(){
	function addFunc(configuration){
		console.log('inside add Func '+configuration);
		AddConfiguration(configuration)
			.then(() => {
				alert("Configuration added successfully");
			})
			.catch(error => {
				console.error('Error adding configuration:', error);
			});
	}
	return (<div className="UserApp">
		<h1>Configuration Management Web App</h1>
		<ConfigurationForm addFunc={addFunc}/>
	</div>);
}

