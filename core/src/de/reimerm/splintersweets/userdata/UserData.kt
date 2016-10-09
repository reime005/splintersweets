/*
 * Copyright (c) 2016. Marius Reimer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.reimerm.splintersweets.userdata

import de.reimerm.splintersweets.enums.Color
import de.reimerm.splintersweets.enums.UserDataType

/**
 * Added to a body - holds information about it. Is an interface between the body and actor.
 *
 * Created by Marius Reimer on 11-Jun-16.
 */
open class UserData {

    lateinit var userDataType: UserDataType
    lateinit var color: Color

    constructor(userDataType: UserDataType) {
        this.userDataType = userDataType
    }
}