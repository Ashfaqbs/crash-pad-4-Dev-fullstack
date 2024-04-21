import React, { useState,useEffect } from 'react'
import {listErrors}from './../../Services/CrashpadBackendService'
import axios from "axios";

const ErrorList = () => {
    const [tableData, settableData] = React.useState([]);
    const [errorCounts, setErrorCounts] = useState({});

    function getAllError() {
        listErrors()
          .then((response) => {
            settableData(response.data);
            console.log(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }



      useEffect(() => {

        getAllError() ;
            


        axios.get('http://localhost:8080/errorCounts')
        .then(response => {
          setErrorCounts(response.data);
        })
        .catch(error => {
          console.error('Error fetching error counts:', error);
        });

    
      }, []);



  return (
    <div>
      <h2>Error List</h2>

      <table>
        <thead>
          <tr>
            <th>Exception Name</th>
            <th>Error Count</th>
          </tr>
        </thead>
        <tbody>
          {Object.entries(errorCounts).map(([exceptionName, count]) => (
            <tr key={exceptionName}>
              <td>{exceptionName}</td>
              <td>{count}</td>
            </tr>
          ))}
        </tbody>
      </table>

      <h2>Detailed Error List</h2>

      <table>
        <thead>
          <tr>
            <th>Exception Name</th>
            <th>Timestamp</th>
            <th>Error Message</th>
            <th>Google Search URL</th>
          </tr>
        </thead>
        <tbody>
          {tableData.map((error, index) => (
            <tr key={index}>
              <td>{error.exceptionName}</td>
              <td>{error.timestamp}</td>
              <td>{error.errorMessage}</td>
              <td><a href={error.googleSearchUrl} target="_blank" rel="noopener noreferrer">Google</a></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default ErrorList
