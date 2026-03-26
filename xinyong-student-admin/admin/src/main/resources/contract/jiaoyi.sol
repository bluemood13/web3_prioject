// SPDX-License-Identifier: MIT
pragma solidity ^0.4.25;

/**
 * Jiaoyi合约：存储和管理Jiaoyi相关数据
 */
contract JiaoyiContract {
    string public jsonStr;

    struct Jiaoyi {
            string num;
            string types; 
            string price;
    }

    Jiaoyi private jiaoyi;

    
    
    function setJsonStr(string memory _jsonStr) public {
        jsonStr = _jsonStr;
    }

   
    function getJsonStr() public view returns (string memory) {
        return jsonStr;
    }

    
    function setJiaoyi(
            string _num,
            string memory _types,
            string _price
    ) public {
        jiaoyi.num = _num;
        jiaoyi.types = _types;
        jiaoyi.price = _price;
    }

 
    function getJiaoyi() public view returns (
        string num,
        string memory types,
        string price
    ) {
        return (
                    jiaoyi.num,
                    jiaoyi.types,
                    jiaoyi.price
        );
    }

  
    function deleteJiaoyi() public {
        delete jiaoyi;
    }
}