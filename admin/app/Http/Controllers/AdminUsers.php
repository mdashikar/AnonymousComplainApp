<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use DB;

class AdminUsers extends Controller
{
    public function showUsers(){

        $users = DB::table('users')->get();

        return $users;
    }

    public function createUsers(Request $request){

        DB::table('users')->insert(
            [
                'username' => $request->username,
                'email' => $request->email,
                'password' => $request->password
            ]
        );

        $response['message']="User Register Successfully!";
        return json_encode($response);
    }

    public function checkLogin(Request $request){

        $users = DB::table('users')
                     ->where('username', '=', $request->username)
                     ->get();

        if(isset($users)){
            $loginSuccess = DB::table('users')
                         ->where('username', '=', $request->username)
                         ->where('password', '=', $request->password)
                         ->get();

        }else{
            
            $response['error'] = "Invalid Username or Password!";
            return json_encode($response);
        }

        return $loginSuccess;

       
    }

    public function addMessage(Request $request){

        DB::table('message')->insert(
            [
                'message' => $request->message
            ]
        );

        $response['message'] ="Complain Recieved Successfully";
        return json_encode($response);
    }

    public function messagePermitted($id){

        DB::table('message')
            ->where('id', $id)
            ->update(['status' => 1]);

    }

    public function deleteMessage($id){

        DB::table('message')->where('id', '=', $id)->delete();

    }

    public function showAuthorityMessage(){

        $message = DB::table('message')->where('status', '=', 1)->get();

        return $message;

    }

    public function showAdminMessage(){

        $message = DB::table('message')->where('status', '=', 0)->get();

        return json_encode( $message );

    }
}

