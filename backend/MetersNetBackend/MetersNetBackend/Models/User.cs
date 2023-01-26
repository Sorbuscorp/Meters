using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MetersNetBackend.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.Linq;
    using System.Threading.Tasks;

   
    /// <summary>
    /// Аккаунт
    /// </summary>
    public class User : BaseEntity
    {
        /// <summary>
        /// Логин
        /// </summary>
        public string Login { get; set; }

        /// <summary>
        /// Пароль
        /// </summary>
        public string Password { get; set; }

        /// <summary>
        /// ХЭШ от пароля
        /// </summary>
        public virtual string PasswordHash { get; set; }


        /// <summary>
        /// Имя Фамилия пользователя
        /// </summary>
        public string FullName { get; set; }

        /// <summary>
        /// Адресс почты
        /// </summary>
        public string Email { get; set; }

        string Adress { get; set; }

        public List<Meter> Meters { get; set; }
    }
}
