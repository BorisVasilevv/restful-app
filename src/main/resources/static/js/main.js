
function getIndex(list, id){
    for(var i=0;i<list.length;i++){
        if(list[i].id==id){
            return i;
        }
    }

    return -1;
}

var productApi= Vue.resource('/product{/id}');

Vue.component('product-form',{
    props: ['products', 'productToForm'],
    data: function(){
        return {
            name: '',
            price: '',
            status: 'In_order',
            id:''
        }
    },
    watch:{
        productToForm: function(value, oldValue){
            this.name=value.name;
            this.price=value.price;
            this.status=value.status;
            this.id=value.id;
        }
    },
    template: '<div id="redactLine">'+
                '<h4>Name: '+
                '<input type="text" v-model="name"/>'+
                ' Price: '+
                '<input type="number" v-model="price"/> '+
                '<select v-model="status"> '+
                    '<option>In_order</option>'+
                    '<option>Bought</option>'+
                '</select> '+
                '<input type="button" value="Save" v-on:click="save"/></h4>'+
            '</div>',
    methods:{
        save: function(){
            var product={name: this.name,
                         price: this.price,
                         status: this.status};
            if(this.id){
                productApi.update({id:this.id}, product).then(result=>
                    result.json().then(data=>{
                        var index=getIndex(this.products, data.id);
                        this.products.splice(index, 1, data);
                    })
                )
            }else{
                productApi.save({}, product).then(result=>
                    result.json().then(data=>{
                           this.products.push(data);
                    })
                )
            }
            this.name='';
            this.price='';
            this.status='In_order';

        }
    }
});

Vue.component('product-view',{
    props: ['product', 'editMethod', 'products'],
    template:
        '<div id="productLine">'+
            '{{product.id}}) {{product.name}}  price: {{product.price.toString()}} status:  {{product.status}} '+
            '<input type="button" value="Edit" v-on:click="edit"> '+
            '<input type="button" value="X" v-on:click="del"> '+
        '</div>',
    methods:{
        edit: function(){
            this.editMethod(this.product);
        },
        del: function(){
            productApi.remove({id:this.product.id}).then(result=>{
                if(result.ok){
                    this.products.splice(this.products.indexOf(this.product),1)
                }
            })
        },

    }
});



Vue.component('products-list', {
    props: ['products'],
    data: function(){
        return{
            product: null
        }
    },
    template:
        '<div id="main">' +
            '<product-form :products="products" :productToForm="product"/>'+
            '<product-view v-for="product in products" :key="product.id" :product="product" :editMethod="methodToEdit"' +
                ':products="products"/>' +
        '</div>',
    created: function(){
        productApi.get().then(result=>
            result.json().then(data=>
                data.forEach(product=>this.products.push(product))
            )
        )
    },
    methods: {
            methodToEdit:function(product){
                this.product=product;
        }
    }
});
var helloApp = new Vue({
  el: '#app',
  template: '<products-list :products="products"/>',
  data: {
    products: []
  }
});
var app2 = new Vue({
  el: '#app-2',
  data: {
    message: 'Вы загрузили эту страницу: ' + new Date().toLocaleString()
  }
});