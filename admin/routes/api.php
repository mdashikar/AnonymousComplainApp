<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::get('/user/show', 'AdminUsers@showUsers');
Route::post('/user/add', 'AdminUsers@createUsers');
Route::post('/user/check', 'AdminUsers@checkLogin');

Route::post('/message/add', 'AdminUsers@addMessage');
Route::get('/message/{id}', 'AdminUsers@messagePermitted');
Route::get('/message/delete/{id}', 'AdminUsers@deleteMessage');
Route::get('/message/authority/show', 'AdminUsers@showAuthorityMessage');
Route::get('/message/admin/show', 'AdminUsers@showAdminMessage');
