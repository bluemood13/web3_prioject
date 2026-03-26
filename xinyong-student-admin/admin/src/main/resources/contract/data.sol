// SPDX-License-Identifier: MIT
pragma solidity ^0.4.25;

contract DataContract {
    string public jsonStr;

    struct Data {
        string message;
    }

    Data data;

    function setJsonStr(string memory _jsonStr) public {
        jsonStr = _jsonStr;
    }

    function getJsonStr() public view returns (string memory) {
        return jsonStr;
    }

    function setData(string memory _message) public {
        data.message = _message;
    }

    function getData() public view returns (string memory message) {
        return (data.message);
    }

    function deleteData() public {
        delete data;
    }
}