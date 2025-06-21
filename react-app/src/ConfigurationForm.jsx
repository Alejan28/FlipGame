
import React from  'react';
import { useState } from 'react';
export default function ConfigurationForm({addFunc}){

    const [column, setColumn] = useState('');
    const [row, setRow] = useState('');
    const [animal, setAnimal] = useState('');
    const[image, setImage]=useState('');

   function handleSubmit (event){

        let config={column:column,
            row:row,
            animal:animal,
            image:image
        }
        console.log('A configuration was submitted: ');
        console.log(config);
         addFunc(config);
        event.preventDefault();
    }
    return(
        <form onSubmit={handleSubmit}>
            <label>
                Column:
                <input type="number" value={column} onChange={e => setColumn(e.target.value)}/>
            </label><br/>
            <label>
                Row:
                <input type="number" value={row} onChange={e => setRow(e.target.value)}/>
            </label><br/>
            <label>
                Animal:
                <input type="text" value={animal} onChange={e => setAnimal(e.target.value)}/>
            </label><br/>
            <label>
                Image:
                <input type="text" value={image} onChange={e => setImage(e.target.value)}/>
            </label><br/>
            <input type="submit" value="Add configuration"/>
        </form>);
}
